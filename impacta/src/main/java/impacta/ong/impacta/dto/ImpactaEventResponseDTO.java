package impacta.ong.impacta.dto;

import java.time.LocalDate;

public record ImpactaEventResponseDTO(
        String id,
        LocalDate date,
        String volunteerName,
        String ongName,
        String city,
        String state,
        String description,
        String period,
        String status
) {
}
