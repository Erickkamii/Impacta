package impacta.ong.impacta.services;

import impacta.ong.impacta.repositories.VolunteerSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VolunteerSkillService {
    @Autowired
    private VolunteerSkillRepository volunteerSkillRepository;

    public void createSkillAndAssign(String skillName, String volunteerId, String description, String level) {
        volunteerSkillRepository.createSkillAndAssign(skillName, volunteerId, description, level);
    }

    public void createSkillAndAssign(String skillName, String volunteerId, String description) {
        createSkillAndAssign(skillName, volunteerId, description, "Basic");
    }
}
