
package cl.milsabores.backend.controller;

import cl.milsabores.backend.model.Boleta;
import cl.milsabores.backend.service.BoletaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/boletas")
public class BoletaController {

    private final BoletaService boletaService;

    public BoletaController(BoletaService boletaService) {
        this.boletaService = boletaService;
    }

    // GET: listar todas las boletas
    @GetMapping
    public List<Boleta> listarBoletas() {
        return boletaService.listarBoletas();
    }

    // GET: buscar boleta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Boleta> buscarPorId(@PathVariable Long id) {
        return boletaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: crear una boleta
    @PostMapping
    public ResponseEntity<Boleta> crearBoleta(
            @RequestBody Boleta boleta) {

        boleta.setIdBoleta(null);
        Boleta nueva = boletaService.guardarBoleta(boleta);

        return ResponseEntity.status(201).body(nueva);
    }

    // PUT: actualizar una boleta
    @PutMapping("/{id}")
    public ResponseEntity<Boleta> actualizarBoleta(
            @PathVariable Long id,
            @RequestBody Boleta boleta) {

        if (boletaService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        boleta.setIdBoleta(id);

        return ResponseEntity.ok(
                boletaService.guardarBoleta(boleta));
    }

    // DELETE: eliminar boleta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarBoleta(@PathVariable Long id) {

        if (boletaService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        boletaService.eliminarBoleta(id);
        return ResponseEntity.noContent().build();
    }
}
