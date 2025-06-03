package impacta.ong.impacta.repositories;

import impacta.ong.impacta.domain.user.Ong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OngRepository extends JpaRepository<Ong, String> {
}
