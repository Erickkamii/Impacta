package impacta.ong.impacta.repositories;

import impacta.ong.impacta.domain.skill.VolunteerSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerSkillRepository extends JpaRepository<VolunteerSkill, String> {
    List<VolunteerSkill> findVolunteerSkillByVolunteerId(String volunteerId);
}
