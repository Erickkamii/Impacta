-- ====================================
-- SCHEMA LIMPO - INTER DATABASE
-- ====================================

-- Tabela de usuários
CREATE TABLE users (
                       id VARCHAR(255) NOT NULL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       document VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(255) NOT NULL,
                       CONSTRAINT users_role_check CHECK (role IN ('ADMIN', 'ONG', 'VOLUNTEER'))
);

-- Tabela de ONGs
CREATE TABLE ong (
                     id VARCHAR(255) NOT NULL PRIMARY KEY,
                     description VARCHAR(255),
                     user_id VARCHAR(255) NOT NULL UNIQUE,
                     CONSTRAINT fk_ong_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Tabela de voluntários
CREATE TABLE volunteer (
                           id VARCHAR(255) NOT NULL PRIMARY KEY,
                           expertise VARCHAR(255),
                           user_id VARCHAR(255) NOT NULL UNIQUE,
                           CONSTRAINT fk_volunteer_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Tabela de habilidades
CREATE TABLE skill (
                       id VARCHAR(255) NOT NULL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL UNIQUE,
                       description VARCHAR(255)
);

-- Tabela de relação voluntário-habilidade
CREATE TABLE volunteer_skill (
                                 id VARCHAR(255) NOT NULL PRIMARY KEY,
                                 volunteer_id VARCHAR(255) NOT NULL,
                                 skill_id VARCHAR(255) NOT NULL,
                                 level VARCHAR(255),
                                 CONSTRAINT fk_volunteer_skill_volunteer FOREIGN KEY (volunteer_id) REFERENCES volunteer(id),
                                 CONSTRAINT fk_volunteer_skill_skill FOREIGN KEY (skill_id) REFERENCES skill(id)
);

-- Tabela de eventos
CREATE TABLE impacta_event (
                               id VARCHAR(255) NOT NULL PRIMARY KEY,
                               ong_id VARCHAR(255) NOT NULL,
                               volunteer_id VARCHAR(255) NOT NULL,
                               description VARCHAR(255),
                               date DATE,
                               period VARCHAR(255),
                               city VARCHAR(255),
                               state VARCHAR(255),
                               status VARCHAR(255),
                               CONSTRAINT fk_event_ong FOREIGN KEY (ong_id) REFERENCES ong(id),
                               CONSTRAINT fk_event_volunteer FOREIGN KEY (volunteer_id) REFERENCES volunteer(id)
);

-- Tabela de doações
CREATE TABLE donation (
                          id VARCHAR(255) NOT NULL PRIMARY KEY,
                          ong_id VARCHAR(255),
                          volunteer_id VARCHAR(255),
                          ong_user_id VARCHAR(255),
                          donation_date DATE,
                          value DOUBLE PRECISION,
                          CONSTRAINT fk_donation_ong FOREIGN KEY (ong_id) REFERENCES ong(id),
                          CONSTRAINT fk_donation_volunteer FOREIGN KEY (volunteer_id) REFERENCES volunteer(id),
                          CONSTRAINT fk_donation_ong_user FOREIGN KEY (ong_user_id) REFERENCES ong(id)
);

-- View de voluntários e habilidades
CREATE VIEW v_volunteer_skill_view AS
SELECT
    v.id AS volunteer_id,
    u.name AS volunteer_name,
    s.id AS skill_id,
    s.name AS skill_name,
    vs.level
FROM volunteer v
         JOIN volunteer_skill vs ON vs.volunteer_id = v.id
         JOIN skill s ON s.id = vs.skill_id
         JOIN users u ON u.id = v.user_id;

-- Tabela materializada da view (se necessário)
CREATE TABLE volunteer_skill_view (
                                      volunteer_id UUID NOT NULL PRIMARY KEY,
                                      volunteer_name VARCHAR(255),
                                      skill_id UUID,
                                      skill_name VARCHAR(255),
                                      level VARCHAR(255)
);

-- ====================================
-- ÍNDICES RECOMENDADOS
-- ====================================

-- Índices para melhor performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_document ON users(document);
CREATE INDEX idx_volunteer_user_id ON volunteer(user_id);
CREATE INDEX idx_ong_user_id ON ong(user_id);
CREATE INDEX idx_volunteer_skill_volunteer ON volunteer_skill(volunteer_id);
CREATE INDEX idx_volunteer_skill_skill ON volunteer_skill(skill_id);
CREATE INDEX idx_event_ong ON impacta_event(ong_id);
CREATE INDEX idx_event_volunteer ON impacta_event(volunteer_id);
CREATE INDEX idx_donation_ong ON donation(ong_id);
CREATE INDEX idx_donation_volunteer ON donation(volunteer_id);

-- ====================================
-- EXTENSIONS E PROCEDURES - INTER DATABASE
-- ====================================

-- Extensão para geração de UUIDs
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Procedure para criar skill e associar ao voluntário
CREATE OR REPLACE PROCEDURE sp_create_skill_and_assign(
    IN p_skill_name VARCHAR,
    IN p_volunteer_id UUID,
    IN p_skill_description VARCHAR,
    IN p_level VARCHAR DEFAULT 'Basic'
)
LANGUAGE plpgsql
AS $$
DECLARE
new_skill_id UUID;
BEGIN
INSERT INTO skill (id, name, description)
VALUES (gen_random_uuid(), p_skill_name, p_skill_description)
    RETURNING id INTO new_skill_id;

INSERT INTO volunteer_skill(id, volunteer_id, skill_id, level)
VALUES (gen_random_uuid(), p_volunteer_id, new_skill_id, p_level);
END;
$$;

-- Function para prevenir skills duplicadas
CREATE OR REPLACE FUNCTION prevent_duplicate_skill()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM volunteer_skill
        WHERE volunteer_id = NEW.volunteer_id
          AND skill_id = NEW.skill_id
    ) THEN
        RAISE EXCEPTION 'Volunteer already has this skill.';
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para aplicar a validação de skills duplicadas
CREATE TRIGGER trg_prevent_duplicate_skill
    BEFORE INSERT ON volunteer_skill
    FOR EACH ROW
    EXECUTE FUNCTION prevent_duplicate_skill();