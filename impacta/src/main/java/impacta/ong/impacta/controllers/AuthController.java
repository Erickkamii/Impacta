package impacta.ong.impacta.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import impacta.ong.impacta.domain.user.User;
import impacta.ong.impacta.domain.user.UserRole;
import impacta.ong.impacta.dto.LoginRequestDTO;
import impacta.ong.impacta.dto.RegisterRequestDTO;
import impacta.ong.impacta.dto.ResponseDTO;
import impacta.ong.impacta.infra.security.TokenService;
import impacta.ong.impacta.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO body) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(body.email(), body.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(new ResponseDTO(user.getUsername(), token));
    }


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
        repository.save(newUser);

        String token = tokenService.generateToken(newUser);
        return ResponseEntity.ok(new ResponseDTO(newUser.getUsername(), token));
    }
}
