package impacta.ong.impacta.controllers;

import impacta.ong.impacta.domain.skill.VolunteerSkill;
import impacta.ong.impacta.repositories.VolunteerSkillRepository;
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

    @PostMapping
    public ResponseEntity<VolunteerSkill> createVolunteerSkill(@RequestBody VolunteerSkill vs) {
        VolunteerSkill saved = volunteerSkillRepository.save(vs);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<VolunteerSkill>> getAllVolunteerSkills() {
        return ResponseEntity.ok(volunteerSkillRepository.findAll());
    }
}
