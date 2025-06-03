package impacta.ong.impacta.dto;



public record ImpactaEventRequestDTO(
        String volunteerId,
        String ongId,
        String city,
        String state,
        String description,
        String period,
        String status
) {
}
