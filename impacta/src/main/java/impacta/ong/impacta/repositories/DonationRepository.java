package impacta.ong.impacta.repositories;

import impacta.ong.impacta.domain.donation.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByVolunteerId(String volunteerId);
    List<Donation> findByOngId(String ongId);

    List<Donation> findByVolunteerUserNameIgnoreCase(String volunteerUserName);
}
