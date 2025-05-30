package impacta.ong.impacta.dto;

import java.time.LocalDate;
import java.util.UUID;

public record DonationResponseDTO(
        String id,
        Double value,
        LocalDate donationDate,
        String volunteerName,
        String ongName
) {
}
