package impacta.ong.impacta.services;

import impacta.ong.impacta.domain.user.VolunteerSkillView;
import impacta.ong.impacta.repositories.VolunteerSkillViewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerSkillViewService {
    private final VolunteerSkillViewRepository repository;

    public VolunteerSkillViewService(VolunteerSkillViewRepository repository) {
        this.repository = repository;
    }

    public List<VolunteerSkillView> findByVolunteerName(String name) {
        return repository.findByVolunteerName(name);
    }

    public List<VolunteerSkillView> findAll() {
        return repository.findAll();
    }
}
