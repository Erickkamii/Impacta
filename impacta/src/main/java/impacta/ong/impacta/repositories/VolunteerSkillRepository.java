package impacta.ong.impacta.repositories;

import impacta.ong.impacta.domain.skill.VolunteerSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerSkillRepository extends JpaRepository<VolunteerSkill, String> {
    List<VolunteerSkill> findVolunteerSkillByVolunteerId(String volunteerId);
    @Procedure(name = "sp_create_skill_and_assign")
    void createSkillAndAssign(
            @Param("p_skill_name") String skillName,
            @Param("p_volunteer_id") String volunteerId,
            @Param("p_skill_description") String description,
            @Param("p_level") String level
    );
}
