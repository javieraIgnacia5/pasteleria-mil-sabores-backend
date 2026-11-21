package cl.milsabores.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CompraDtos {

    public record ItemCompra(
            @NotNull Long idProducto,
            @NotNull @Min(1) Integer cantidad) {}

    public record CompraRequest(
            @NotEmpty List<@Valid ItemCompra> items) {}

    public record DetalleCompra(
            String producto, Integer cantidad,
            BigDecimal precioUnitario, BigDecimal subtotal) {}

    public record CompraResponse(
            Long idBoleta, LocalDateTime fecha,
            BigDecimal total, List<DetalleCompra> detalles) {}
}