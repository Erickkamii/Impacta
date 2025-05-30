package impacta.ong.impacta.domain.donation;

import impacta.ong.impacta.domain.user.Ong;
import impacta.ong.impacta.domain.user.Volunteer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
