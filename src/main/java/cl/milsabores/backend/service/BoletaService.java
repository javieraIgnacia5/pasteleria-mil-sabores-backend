
package cl.milsabores.backend.service;

import cl.milsabores.backend.model.Boleta;
import cl.milsabores.backend.repository.BoletaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BoletaService {

    private final BoletaRepository boletaRepository;

    public BoletaService(BoletaRepository boletaRepository) {
        this.boletaRepository = boletaRepository;
    }

    // Listar todas las boletas
    public List<Boleta> listarBoletas() {
        return boletaRepository.findAll();
    }

    // Buscar boleta por ID
    public Optional<Boleta> buscarPorId(Long id) {
        return boletaRepository.findById(id);
    }

    // Guardar boleta con validaciones
    public Boleta guardarBoleta(Boleta boleta) {

        if (boleta.getUsuario() == null) {
            throw new IllegalArgumentException(
                    "La boleta debe tener un usuario");
        }

        if (boleta.getFecha() == null) {
            throw new IllegalArgumentException(
                    "La fecha de la boleta es obligatoria");
        }

        if (boleta.getTotal() == null ||
                boleta.getTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El total de la boleta no puede ser negativo");
        }

        return boletaRepository.save(boleta);
    }

    // Eliminar boleta por ID
    public void eliminarBoleta(Long id) {
        boletaRepository.deleteById(id);
    }
}
