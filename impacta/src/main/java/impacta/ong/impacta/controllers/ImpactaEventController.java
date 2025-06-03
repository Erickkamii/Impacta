package impacta.ong.impacta.controllers;

import impacta.ong.impacta.domain.event.ImpactaEvent;
import impacta.ong.impacta.dto.ImpactaEventRequestDTO;
import impacta.ong.impacta.dto.ImpactaEventResponseDTO;
import impacta.ong.impacta.repositories.ImpactaEventRepository;
import impacta.ong.impacta.services.ImpactaEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class ImpactaEventController {
    @Autowired
    private ImpactaEventRepository eventRepository;
    @Autowired
    private ImpactaEventService impactaEventService;

    @PostMapping
    public ResponseEntity<ImpactaEventResponseDTO> createEvent(@RequestBody ImpactaEventRequestDTO request) {
        ImpactaEventResponseDTO response = impactaEventService.createImpactaEvent(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ImpactaEventResponseDTO>> getAllEvents() {
        List<ImpactaEvent> events = eventRepository.findAll();

        List<ImpactaEventResponseDTO> response = events.stream()
                .map(event -> new ImpactaEventResponseDTO(
                        event.getId(),//id
                        event.getDate(),//date
                        event.getVolunteer().getUser().getName(),//volunteer name
                        event.getOng().getUser().getName(),//ong name
                        event.getCity(),//city
                        event.getState(),
                        event.getDescription(),
                        event.getPeriod(),
                        event.getStatus()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }
}
