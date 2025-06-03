package impacta.ong.impacta.repositories;

import impacta.ong.impacta.domain.user.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, String> {
}
