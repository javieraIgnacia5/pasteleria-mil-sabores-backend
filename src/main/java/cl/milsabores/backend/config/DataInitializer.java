package cl.milsabores.backend.config;

import cl.milsabores.backend.model.Usuario;
import cl.milsabores.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.findByCorreo("admin@milsabores.cl").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setCorreo("admin@milsabores.cl");
            admin.setPasswordHash(passwordEncoder.encode("Admin1234"));
            admin.setRol("ADMIN");
            usuarioRepository.save(admin);
        }
    }
}