package impacta.ong.impacta.dto;

import impacta.ong.impacta.domain.user.UserRole;
import jakarta.validation.constraints.NotNull;

public record RegisterRequestDTO(String name, String email, String password, String document, @NotNull UserRole role) {

}
