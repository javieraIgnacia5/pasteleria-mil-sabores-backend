package cl.milsabores.backend.controller;

import cl.milsabores.backend.dto.CompraDtos.*;
import cl.milsabores.backend.service.CompraService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<CompraResponse> comprar(@Valid @RequestBody CompraRequest req,
                                                  Authentication authentication) {
        return ResponseEntity.status(201)
                .body(compraService.comprar(authentication.getName(), req));
    }
}