package impacta.ong.impacta.dto;

import java.util.UUID;

public record ImpactaEventRequestDTO(
        UUID volunteerId,
        UUID ongId,
        String city,
        String state,
        String description,
        String period
) {
}
