package com.gestion.service;

import com.gestion.dto.ProductoDTO;
import com.gestion.dto.ProveedorDTO;
import com.gestion.entity.Producto;
import com.gestion.entity.Proveedor;
import com.gestion.repository.ProductoRepository;
import com.gestion.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProveedorService proveedorService; // Inyectar el servicio de proveedores

    public List<ProductoDTO> findAll() {
        return productoRepository.findAll().stream()
                .map(producto -> new ProductoDTO(producto.getId(), producto.getNombre(), producto.getDescripcion(), producto.getPrecio(), producto.getStock(), producto.getProveedor()))
                .collect(Collectors.toList());
    }

    public ProductoDTO findById(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.map(p -> new ProductoDTO(p.getId(), p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getStock(), p.getProveedor()))
                .orElse(null);
    }


   public ProductoDTO save(ProductoDTO productoDTO) {
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());

        // Crear o actualizar el proveedor
        Proveedor proveedor = productoDTO.getProveedor();
        if (proveedor != null) {
            proveedor = proveedorService.save(proveedor); // Guardar el proveedor
            producto.setProveedor(proveedor);
        }

        return new ProductoDTO(productoRepository.save(producto).getId(), producto.getNombre(), producto.getDescripcion(), producto.getPrecio(), producto.getStock(), producto.getProveedor());
    }




    public ProductoDTO update(Long id, ProductoDTO productoDTO) {
        Optional<Producto> optionalProducto = productoRepository.findById(id);
        if (optionalProducto.isPresent()) {
            Producto producto = optionalProducto.get();
            producto.setNombre(productoDTO.getNombre());
            producto.setDescripcion(productoDTO.getDescripcion());
            producto.setPrecio(productoDTO.getPrecio());
            producto.setStock(productoDTO.getStock());

            // Crear o actualizar el proveedor
            Proveedor proveedor = productoDTO.getProveedor();
            if (proveedor != null) {
                proveedor = proveedorService.save(proveedor); // Guardar el proveedor
                producto.setProveedor(proveedor);
            }

            return new ProductoDTO(productoRepository.save(producto).getId(), producto.getNombre(), producto.getDescripcion(), producto.getPrecio(), producto.getStock(), producto.getProveedor());
        }
        return null;
    }


    public void delete(Long id) {
        productoRepository.deleteById(id);
    }
}