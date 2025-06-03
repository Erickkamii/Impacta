package impacta.ong.impacta.dto;


import impacta.ong.impacta.domain.skill.Skill;

public record SkillResponseDTO(
        String id,
        String name,
        String description
) {
    public SkillResponseDTO(Skill skill) {
        this(skill.getId(), skill.getName(), skill.getDescription());
    }
}
