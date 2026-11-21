package cl.milsabores.backend.service;

import cl.milsabores.backend.dto.AuthDtos.*;
import cl.milsabores.backend.model.Usuario;
import cl.milsabores.backend.repository.UsuarioRepository;
import cl.milsabores.backend.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse registrar(RegistroRequest req) {
        String correo = req.correo().trim().toLowerCase();

        if (usuarioRepository.findByCorreo(correo).isPresent()) {
            throw new IllegalStateException("Ya existe una cuenta con ese correo");
        }

        Usuario u = new Usuario();
        u.setNombre(req.nombre().trim());
        u.setCorreo(correo);
        u.setPasswordHash(passwordEncoder.encode(req.password()));
        u.setRol("CLIENTE");
        u = usuarioRepository.save(u);

        return respuesta(u);
    }

    public AuthResponse login(LoginRequest req) {
        Usuario u = usuarioRepository
                .findByCorreo(req.correo().trim().toLowerCase())
                .filter(x -> passwordEncoder.matches(req.password(), x.getPasswordHash()))
                .orElseThrow(() -> new BadCredentialsException(
                        "Correo o contraseña incorrectos"));

        return respuesta(u);
    }

    public AuthResponse renovar(String correo) {
        Usuario u = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));
        return respuesta(u);
    }

    private AuthResponse respuesta(Usuario u) {
        String token = jwtService.generarToken(u.getCorreo(), u.getRol());
        return new AuthResponse(token, "Bearer", u.getIdUsuario(),
                u.getNombre(), u.getCorreo(), u.getRol());
    }
}