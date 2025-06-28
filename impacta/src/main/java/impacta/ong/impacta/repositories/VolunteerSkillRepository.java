package impacta.ong.impacta.repositories;

import impacta.ong.impacta.domain.skill.VolunteerSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface VolunteerSkillRepository extends JpaRepository<VolunteerSkill, String> {
    List<VolunteerSkill> findVolunteerSkillByVolunteerId(String volunteerId);

    @Modifying
    @Transactional
    @Query(value = "CALL sp_create_skill_and_assign(:skillName, :volunteerId, :description, :level)", nativeQuery = true)
    void createSkillAndAssign(
            @Param("skillName") String skillName,
            @Param("volunteerId") String volunteerId,  // Agora sem cast
            @Param("description") String description,
            @Param("level") String level
    );
}