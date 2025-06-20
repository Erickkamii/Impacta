package impacta.ong.impacta.controllers;

import impacta.ong.impacta.domain.user.Ong;
import impacta.ong.impacta.dto.OngResponseDTO;
import impacta.ong.impacta.repositories.OngRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ong")
@RequiredArgsConstructor
public class OngController {
    @Autowired
    private final OngRepository ongRepository;
    @GetMapping
    public ResponseEntity<List<OngResponseDTO>> getAllOngs() {
        List<Ong> ongs = ongRepository.findAll();

        List<OngResponseDTO> response = ongs.stream()
                .map(ong -> new OngResponseDTO(
                        ong.getId(),
                        ong.getUser().getName() // ou getUser().getNome(), dependendo do seu User
                ))
                .toList();

        return ResponseEntity.ok(response);
    }
}
