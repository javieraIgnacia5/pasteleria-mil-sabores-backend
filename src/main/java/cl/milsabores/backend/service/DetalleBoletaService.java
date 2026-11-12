
package cl.milsabores.backend.service;

import cl.milsabores.backend.model.DetalleBoleta;
import cl.milsabores.backend.repository.DetalleBoletaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class DetalleBoletaService {

    private final DetalleBoletaRepository detalleBoletaRepository;

    public DetalleBoletaService(
            DetalleBoletaRepository detalleBoletaRepository) {
        this.detalleBoletaRepository = detalleBoletaRepository;
    }

    public List<DetalleBoleta> listarDetalles() {
        return detalleBoletaRepository.findAll();
    }

    public Optional<DetalleBoleta> buscarPorId(Long id) {
        return detalleBoletaRepository.findById(id);
    }

    public DetalleBoleta guardarDetalle(DetalleBoleta detalle) {

        if (detalle.getBoleta() == null) {
            throw new IllegalArgumentException(
                    "La boleta es obligatoria");
        }

        if (detalle.getProducto() == null) {
            throw new IllegalArgumentException(
                    "El producto es obligatorio");
        }

        if (detalle.getCantidad() == null ||
                detalle.getCantidad() <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor a cero");
        }

        if (detalle.getPrecioUnitario() == null ||
                detalle.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "El precio debe ser mayor a cero");
        }

        BigDecimal subtotal = detalle.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(detalle.getCantidad()));

        detalle.setSubtotal(subtotal);

        return detalleBoletaRepository.save(detalle);
    }

    public void eliminarDetalle(Long id) {
        detalleBoletaRepository.deleteById(id);
    }
}
