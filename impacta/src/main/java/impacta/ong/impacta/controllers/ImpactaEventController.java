package impacta.ong.impacta.controllers;

import impacta.ong.impacta.domain.event.ImpactaEvent;
import impacta.ong.impacta.repositories.ImpactaEventRepository;
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

    @PostMapping
    public ResponseEntity<ImpactaEvent> createEvent(@RequestBody ImpactaEvent event) {
        ImpactaEvent saved = eventRepository.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<ImpactaEvent>> getAllEvents() {
        return ResponseEntity.ok(eventRepository.findAll());
    }
}
