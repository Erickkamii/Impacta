package impacta.ong.impacta.controllers;

import impacta.ong.impacta.domain.skill.VolunteerSkill;
import impacta.ong.impacta.domain.user.VolunteerSkillView;
import impacta.ong.impacta.dto.AssignSkillRequestDTO;
import impacta.ong.impacta.repositories.VolunteerSkillRepository;
import impacta.ong.impacta.repositories.VolunteerSkillViewRepository;
import impacta.ong.impacta.services.VolunteerSkillService;
import impacta.ong.impacta.services.VolunteerSkillViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volunteer-skill")
public class VolunteerSkillController {

    @Autowired
    private VolunteerSkillRepository volunteerSkillRepository;
    @Autowired
    private VolunteerSkillService volunteerSkillService;

    @Autowired
    private VolunteerSkillViewService volunteerSkillViewService;

    @Autowired
    private VolunteerSkillViewRepository volunteerSkillViewRepository;


    @PostMapping
    public ResponseEntity<String> createVolunteerSkill(@RequestBody AssignSkillRequestDTO request) {
        try {
            if (request.level() == null || request.level().isEmpty()) {
                volunteerSkillService.createSkillAndAssign(
                        request.skillName(),
                        request.volunteerId(),
                        request.description()
                );
            } else {
                volunteerSkillService.createSkillAndAssign(
                        request.skillName(),
                        request.volunteerId(),
                        request.description(),
                        request.level()
                );
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Skill created and assigned successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating skill: " + e.getMessage());
        }
    }

    @GetMapping("/{volunteerId}")
    public ResponseEntity<List<VolunteerSkillView>> getVolunteerSkillViewsByVolunteerId(@PathVariable String volunteerId) {
        List<VolunteerSkillView> list = volunteerSkillViewService.findByVolunteerId(volunteerId);
        return ResponseEntity.ok(list);
    }
}
