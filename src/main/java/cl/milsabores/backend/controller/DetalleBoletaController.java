
package cl.milsabores.backend.controller;

import cl.milsabores.backend.model.DetalleBoleta;
import cl.milsabores.backend.service.DetalleBoletaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/detalles-boleta")
public class DetalleBoletaController {

    private final DetalleBoletaService detalleService;

    public DetalleBoletaController(DetalleBoletaService detalleService) {
        this.detalleService = detalleService;
    }

    // GET: listar detalles
    @GetMapping
    public List<DetalleBoleta> listarDetalles() {
        return detalleService.listarDetalles();
    }

    // GET: buscar detalle por ID
    @GetMapping("/{id}")
    public ResponseEntity<DetalleBoleta> buscarPorId(
            @PathVariable Long id) {

        return detalleService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: crear detalle
    @PostMapping
    public ResponseEntity<DetalleBoleta> crearDetalle(
            @RequestBody DetalleBoleta detalle) {

        detalle.setIdDetalle(null);
        DetalleBoleta nuevo = detalleService.guardarDetalle(detalle);

        return ResponseEntity.status(201).body(nuevo);
    }

    // PUT: actualizar detalle
    @PutMapping("/{id}")
    public ResponseEntity<DetalleBoleta> actualizarDetalle(
            @PathVariable Long id,
            @RequestBody DetalleBoleta detalle) {

        if (detalleService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        detalle.setIdDetalle(id);

        return ResponseEntity.ok(
                detalleService.guardarDetalle(detalle));
    }

    // DELETE: eliminar detalle
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalle(
            @PathVariable Long id) {

        if (detalleService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        detalleService.eliminarDetalle(id);
        return ResponseEntity.noContent().build();
    }
}
