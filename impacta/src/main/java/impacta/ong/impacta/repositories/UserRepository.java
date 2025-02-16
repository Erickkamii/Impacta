package impacta.ong.impacta.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import impacta.ong.impacta.domain.user.User;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
