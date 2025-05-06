package impacta.ong.impacta.repositories;

import impacta.ong.impacta.domain.donation.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByVolunteerId(String volunteerId);
    List<Donation> findByOngId(String ongId);
}
