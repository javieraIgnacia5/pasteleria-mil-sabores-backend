
package cl.milsabores.backend.service;

import cl.milsabores.backend.model.Producto;
import cl.milsabores.backend.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Listar todos los productos
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Buscar un producto por ID
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Guardar un producto
    public Producto guardarProducto(Producto producto) {

        if (producto.getNombre() == null ||
                producto.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del producto es obligatorio");
        }

        if (producto.getPrecio() == null ||
                producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "El precio debe ser mayor a cero");
        }

        if (producto.getStock() == null ||
                producto.getStock() < 0) {
            throw new IllegalArgumentException(
                    "El stock no puede ser negativo");
        }

        if (producto.getCategoria() == null) {
            throw new IllegalArgumentException(
                    "El producto debe tener una categoría");
        }

        return productoRepository.save(producto);
    }

    // Eliminar un producto por ID
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
