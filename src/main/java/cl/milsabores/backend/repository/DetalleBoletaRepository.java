package cl.milsabores.backend.repository;

import cl.milsabores.backend.model.DetalleBoleta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleBoletaRepository
        extends JpaRepository<DetalleBoleta, Long> {
}
