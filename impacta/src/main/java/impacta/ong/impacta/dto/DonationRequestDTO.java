package impacta.ong.impacta.dto;


import java.time.LocalDate;

public record DonationRequestDTO(
        String volunteerId,
        String ongId,
        Double value,
        LocalDate donationDate
) {}
