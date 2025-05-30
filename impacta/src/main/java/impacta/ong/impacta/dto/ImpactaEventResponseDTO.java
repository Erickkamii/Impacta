package impacta.ong.impacta.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ImpactaEventResponseDTO(
        UUID id,
        LocalDate date,
        UUID volunteerId,
        UUID ongId,
        String city,
        String state,
        String description,
        String period,
        String status
) {
}
