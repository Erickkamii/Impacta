package impacta.ong.impacta.repositories;

import impacta.ong.impacta.domain.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
