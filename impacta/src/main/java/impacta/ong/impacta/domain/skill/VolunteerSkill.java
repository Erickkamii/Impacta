package impacta.ong.impacta.domain.skill;

import impacta.ong.impacta.domain.user.Volunteer;
import jakarta.persistence.*;

@Entity
public class VolunteerSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Volunteer volunteer;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Skill skill;

    private String level;
}
