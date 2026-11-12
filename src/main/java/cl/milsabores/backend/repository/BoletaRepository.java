package cl.milsabores.backend.repository;

import cl.milsabores.backend.model.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoletaRepository
        extends JpaRepository<Boleta, Long> {
}
