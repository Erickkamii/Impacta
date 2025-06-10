package impacta.ong.impacta.controllers;

import impacta.ong.impacta.dto.AssignSkillRequestDTO;
import impacta.ong.impacta.dto.SkillRequestDTO;
import impacta.ong.impacta.dto.SkillResponseDTO;
import impacta.ong.impacta.repositories.SkillRepository;
import impacta.ong.impacta.services.SkillService;
import impacta.ong.impacta.services.VolunteerSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private VolunteerSkillService volunteerSkillService;

    @PostMapping
    public ResponseEntity<SkillResponseDTO> createSkill(@RequestBody SkillRequestDTO skillRequest) {
        SkillResponseDTO savedSkill = skillService.createSkill(skillRequest);
        return ResponseEntity.status(201).body(savedSkill);
    }

    @GetMapping
    public ResponseEntity<List<SkillResponseDTO>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponseDTO> getSkillById(@PathVariable String id) {
        return ResponseEntity.ok(skillService.getSkillById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillResponseDTO> updateSkill(@PathVariable String id, @RequestBody SkillRequestDTO skillRequest) {
        return ResponseEntity.ok(skillService.updateSkill(id, skillRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable String id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/assign")
    public ResponseEntity<String> assignSkillToVolunteer(@RequestBody AssignSkillRequestDTO dto) {
        volunteerSkillService.createSkillAndAssign(
                dto.skillName(),
                dto.volunteerId(),
                dto.description(),
                dto.level()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
