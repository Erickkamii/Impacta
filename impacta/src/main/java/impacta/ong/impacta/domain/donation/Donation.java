package impacta.ong.impacta.domain.donation;

import impacta.ong.impacta.domain.user.Ong;
import impacta.ong.impacta.domain.user.Volunteer;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private Volunteer volunteer;
    @ManyToOne
    private Ong ong;

    private Double value;

    private LocalDate donationDate;
}
