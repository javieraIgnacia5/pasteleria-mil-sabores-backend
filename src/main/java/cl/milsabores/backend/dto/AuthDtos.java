package cl.milsabores.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDtos {

    public record RegistroRequest(
            @NotBlank String nombre,
            @NotBlank @Email String correo,
            @NotBlank @Size(min = 6, max = 50) String password) {}

    public record LoginRequest(
            @NotBlank String correo,
            @NotBlank String password) {}

    public record AuthResponse(
            String token, String tipo, Long idUsuario,
            String nombre, String correo, String rol) {}
}