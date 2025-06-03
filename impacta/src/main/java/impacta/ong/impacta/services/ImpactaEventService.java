package impacta.ong.impacta.services;

import impacta.ong.impacta.domain.event.ImpactaEvent;
import impacta.ong.impacta.domain.user.Ong;
import impacta.ong.impacta.domain.user.Volunteer;
import impacta.ong.impacta.dto.ImpactaEventRequestDTO;
import impacta.ong.impacta.dto.ImpactaEventResponseDTO;
import impacta.ong.impacta.repositories.ImpactaEventRepository;
import impacta.ong.impacta.repositories.OngRepository;
import impacta.ong.impacta.repositories.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ImpactaEventService {
    @Autowired
    private ImpactaEventRepository impactaEventRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private OngRepository ongRepository;

    public ImpactaEventResponseDTO createImpactaEvent(ImpactaEventRequestDTO impactaEventRequest) {
        Volunteer volunteer = volunteerRepository.findById(impactaEventRequest.volunteerId())
                .orElseThrow(()-> new RuntimeException("Volunteer not found"));
        Ong ong = ongRepository.findById(impactaEventRequest.ongId())
                .orElseThrow(()-> new RuntimeException("Ong not found"));
        ImpactaEvent impactaEvent = new ImpactaEvent();
        impactaEvent.setOng(ong);
        impactaEvent.setVolunteer(volunteer);
        impactaEvent.setCity(impactaEventRequest.city());
        impactaEvent.setState(impactaEventRequest.state());
        impactaEvent.setDescription(impactaEventRequest.description());
        impactaEvent.setPeriod(impactaEventRequest.period());
        impactaEvent.setDate(LocalDate.now());
        impactaEvent.setStatus(impactaEventRequest.status());

        impactaEventRepository.save(impactaEvent);

        return new ImpactaEventResponseDTO(
                impactaEvent.getId(),
                impactaEvent.getDate(),
                volunteer.getUser().getName(),
                ong.getUser().getName(),
                impactaEvent.getCity(),
                impactaEvent.getState(),
                impactaEvent.getDescription(),
                impactaEvent.getPeriod(),
                impactaEvent.getStatus()
        );
    }
}
