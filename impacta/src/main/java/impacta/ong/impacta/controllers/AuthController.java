package impacta.ong.impacta.controllers;

import impacta.ong.impacta.domain.user.Ong;
import impacta.ong.impacta.domain.user.Volunteer;
import impacta.ong.impacta.repositories.OngRepository;
import impacta.ong.impacta.repositories.VolunteerRepository;
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


}
