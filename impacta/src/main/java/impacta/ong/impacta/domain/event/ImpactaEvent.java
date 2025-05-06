package impacta.ong.impacta.domain.event;

import impacta.ong.impacta.domain.user.Ong;
import impacta.ong.impacta.domain.user.Volunteer;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class ImpactaEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "volunteer_id", nullable = false)
    private Volunteer volunteer;
    @ManyToOne
    @JoinColumn(name = "ong_id", nullable = false)
    private Ong ong;

    private String status;

    private String city;

    private String state;

    private String description;

    private String period;
}
