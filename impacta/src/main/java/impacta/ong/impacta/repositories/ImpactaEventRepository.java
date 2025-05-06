package impacta.ong.impacta.repositories;

import impacta.ong.impacta.domain.event.ImpactaEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImpactaEventRepository extends JpaRepository<ImpactaEvent, Long> {
}
