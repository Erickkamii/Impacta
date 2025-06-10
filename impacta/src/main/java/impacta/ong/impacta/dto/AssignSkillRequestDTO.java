package impacta.ong.impacta.dto;

public record AssignSkillRequestDTO (
    String skillName,
    String volunteerId,
    String description,
    String level){
}
