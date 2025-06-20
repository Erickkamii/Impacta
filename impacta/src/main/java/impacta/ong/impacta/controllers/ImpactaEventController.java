package impacta.ong.impacta.controllers;

import impacta.ong.impacta.domain.event.ImpactaEvent;
import impacta.ong.impacta.domain.user.Volunteer;
import impacta.ong.impacta.dto.ImpactaEventRequestDTO;
import impacta.ong.impacta.dto.ImpactaEventResponseDTO;
import impacta.ong.impacta.repositories.ImpactaEventRepository;
import impacta.ong.impacta.repositories.VolunteerRepository;
import impacta.ong.impacta.services.ImpactaEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class ImpactaEventController {
    @Autowired
    private ImpactaEventRepository eventRepository;
    @Autowired
    private ImpactaEventService impactaEventService;
    @Autowired
    private VolunteerRepository volunteerRepository;

    @PostMapping
    public ResponseEntity<ImpactaEventResponseDTO> createEvent(@RequestBody ImpactaEventRequestDTO request) {
        ImpactaEventResponseDTO response = impactaEventService.createImpactaEvent(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{eventId}/register")
    public ResponseEntity<String> registerVolunteerInEvent(
            @PathVariable Long eventId,
            @RequestBody Map<String, String> request) {

        String volunteerId = request.get("volunteerId");

        ImpactaEvent event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Verificar se o evento já tem voluntário
        if (event.getVolunteer() != null) {
            return ResponseEntity.badRequest().body("Este evento já possui um voluntário inscrito!");
        }

        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("Volunteer not found"));

        event.setVolunteer(volunteer);
        eventRepository.save(event);

        return ResponseEntity.ok("Inscrito com sucesso!");
    }

    @GetMapping
    public ResponseEntity<List<ImpactaEventResponseDTO>> getAllEvents() {
        List<ImpactaEvent> events = eventRepository.findAll();

        List<ImpactaEventResponseDTO> response = events.stream()
                .map(event -> new ImpactaEventResponseDTO(
                        event.getId(),
                        event.getDate(),
                        event.getVolunteer().getUser().getName(),
                        event.getOng().getUser().getName(),
                        event.getCity(),
                        event.getState(),
                        event.getDescription(),
                        event.getPeriod(),
                        event.getStatus()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }
}
