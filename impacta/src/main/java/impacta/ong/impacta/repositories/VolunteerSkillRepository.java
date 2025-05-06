package impacta.ong.impacta.repositories;

import impacta.ong.impacta.domain.skill.VolunteerSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolunteerSkillRepository extends JpaRepository<VolunteerSkill, String> {
    List<VolunteerSkill> findVolunteerSkillByVolunteerId(String volunteerId);
}
