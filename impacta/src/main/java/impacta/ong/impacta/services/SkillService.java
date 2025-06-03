package impacta.ong.impacta.services;

import impacta.ong.impacta.domain.skill.Skill;
import impacta.ong.impacta.dto.SkillRequestDTO;
import impacta.ong.impacta.dto.SkillResponseDTO;
import impacta.ong.impacta.repositories.SkillRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService {

    @Autowired
    private SkillRepository repository;

    public SkillResponseDTO createSkill(SkillRequestDTO skillRequest){
        Skill skill = new Skill();
        skill.setName(skillRequest.name());
        skill.setDescription(skillRequest.description());
        repository.save(skill);
        return new SkillResponseDTO(skill);
    }

    public List<SkillResponseDTO> getAllSkills(){
        return repository.findAll()
                .stream()
                .map(SkillResponseDTO::new)
                .collect(Collectors.toList());
    }

    public SkillResponseDTO getSkillById(String id){
        Skill skill = repository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Skill not found"));
        return new SkillResponseDTO(skill);
    }

    public SkillResponseDTO updateSkill(String id, SkillRequestDTO skillRequest){
        Skill skill = repository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Skill not found"));
        skill.setName(skillRequest.name());
        skill.setDescription(skillRequest.description());
        repository.save(skill);
        return new SkillResponseDTO(skill);
    }

    public void deleteSkill(String id){
        if(!repository.existsById(id)){
            throw new EntityNotFoundException("Skill not found");
        }
        repository.deleteById(id);
    }
}
