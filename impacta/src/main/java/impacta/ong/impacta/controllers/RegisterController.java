package impacta.ong.impacta.controllers;

import impacta.ong.impacta.domain.user.Ong;
import impacta.ong.impacta.domain.user.User;
import impacta.ong.impacta.domain.user.UserRole;
import impacta.ong.impacta.domain.user.Volunteer;
import impacta.ong.impacta.dto.RegisterRequestDTO;
import impacta.ong.impacta.dto.ResponseDTO;
import impacta.ong.impacta.infra.security.TokenService;
import impacta.ong.impacta.repositories.OngRepository;
import impacta.ong.impacta.repositories.UserRepository;
import impacta.ong.impacta.repositories.VolunteerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegisterController {
    private final UserRepository repository;
    private final VolunteerRepository volunteerRepository;
    private final OngRepository ongRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO body) {
        var userOptional = repository.findByEmail(body.email());

        if (body.name() == null || body.name().isBlank() ||
                body.document() == null || body.document().isBlank() ||
                body.role() == null) {
            return ResponseEntity.badRequest().body(new ResponseDTO("Preencha todos os campos obrigatórios", null));
        }

        if (userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Email em uso");
        }

        User newUser = new User();
        newUser.setEmail(body.email());
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setName(body.name());
        newUser.setDocument(body.document());
        newUser.setRole(body.role());

        User savedUser = repository.save(newUser);

        if (savedUser.getRole() == UserRole.VOLUNTEER) {
            Volunteer volunteer = new Volunteer();
            volunteer.setUser(savedUser);
            volunteerRepository.saveAndFlush(volunteer);
        } else if (savedUser.getRole() == UserRole.ONG) {
            Ong ong = new Ong();
            ong.setUser(savedUser);
            ongRepository.saveAndFlush(ong);
        }
        String token = tokenService.generateToken(newUser);
        return ResponseEntity.ok(new ResponseDTO(newUser.getUsername(), token));
    }
}
