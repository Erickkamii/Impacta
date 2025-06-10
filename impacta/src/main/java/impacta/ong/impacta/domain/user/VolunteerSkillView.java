package impacta.ong.impacta.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "v_volunteer_skill_view")
public class VolunteerSkillView {
    @Id
    @Column(name = "volunteer_id")
    private String volunteerId;

    @Column(name = "volunteer_name")
    private String volunteerName;
    @Column(name = "skill_id")
    private String skillId;
    @Column(name = "skill_name")
    private String skillName;
    @Column(name = "level")
    private String level;
}
