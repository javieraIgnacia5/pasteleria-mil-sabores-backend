package cl.milsabores.backend.service;

import cl.milsabores.backend.dto.CompraDtos.*;
import cl.milsabores.backend.model.Boleta;
import cl.milsabores.backend.model.DetalleBoleta;
import cl.milsabores.backend.model.Producto;
import cl.milsabores.backend.model.Usuario;
import cl.milsabores.backend.repository.BoletaRepository;
import cl.milsabores.backend.repository.DetalleBoletaRepository;
import cl.milsabores.backend.repository.ProductoRepository;
import cl.milsabores.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompraService {

    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final BoletaRepository boletaRepository;
    private final DetalleBoletaRepository detalleRepository;

    public CompraService(UsuarioRepository usuarioRepository,
                         ProductoRepository productoRepository,
                         BoletaRepository boletaRepository,
                         DetalleBoletaRepository detalleRepository) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.boletaRepository = boletaRepository;
        this.detalleRepository = detalleRepository;
    }

    // Todo o nada: si algo falla, se deshace la compra completa
    @Transactional
    public CompraResponse comprar(String correo, CompraRequest req) {

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Boleta boleta = new Boleta();
        boleta.setUsuario(usuario);
        boleta.setFecha(LocalDateTime.now());
        boleta.setTotal(BigDecimal.ZERO);
        boleta = boletaRepository.save(boleta);

        BigDecimal total = BigDecimal.ZERO;
        List<DetalleCompra> detalles = new ArrayList<>();

        for (ItemCompra item : req.items()) {

            Producto producto = productoRepository.findById(item.idProducto())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "El producto " + item.idProducto() + " no existe"));

            if (producto.getStock() < item.cantidad()) {
                throw new IllegalArgumentException(
                        "Stock insuficiente para " + producto.getNombre());
            }

            // El precio sale de la base de datos, nunca del cliente
            BigDecimal subtotal = producto.getPrecio()
                    .multiply(BigDecimal.valueOf(item.cantidad()));

            producto.setStock(producto.getStock() - item.cantidad());
            productoRepository.save(producto);

            DetalleBoleta detalle = new DetalleBoleta();
            detalle.setBoleta(boleta);
            detalle.setProducto(producto);
            detalle.setCantidad(item.cantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(subtotal);
            detalleRepository.save(detalle);

            total = total.add(subtotal);
            detalles.add(new DetalleCompra(producto.getNombre(), item.cantidad(),
                    producto.getPrecio(), subtotal));
        }

        boleta.setTotal(total);
        boletaRepository.save(boleta);

        return new CompraResponse(boleta.getIdBoleta(), boleta.getFecha(), total, detalles);
    }
}