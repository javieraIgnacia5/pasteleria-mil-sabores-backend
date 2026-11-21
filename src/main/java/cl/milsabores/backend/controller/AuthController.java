package cl.milsabores.backend.controller;

import cl.milsabores.backend.dto.AuthDtos.*;
import cl.milsabores.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registro(@Valid @RequestBody RegistroRequest req) {
        return ResponseEntity.status(201).body(authService.registrar(req));
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }

    // Renueva el token (hay que enviar un token todavía válido)
    @PostMapping("/refresh")
    public AuthResponse refresh(Authentication authentication) {
        return authService.renovar(authentication.getName());
    }
}