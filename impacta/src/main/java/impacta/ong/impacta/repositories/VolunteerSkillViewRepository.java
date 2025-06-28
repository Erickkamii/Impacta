package impacta.ong.impacta.repositories;

import impacta.ong.impacta.domain.user.VolunteerSkillView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolunteerSkillViewRepository extends JpaRepository<VolunteerSkillView, String> {
    List<VolunteerSkillView> findByVolunteerName(String volunteerName);

    List<VolunteerSkillView> findByVolunteerId(String volunteerId);
}
