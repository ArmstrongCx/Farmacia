package com.gestion.SERVICE;

import com.gestion.dto.ProductoDTO;
import com.gestion.entity.Producto;
import com.gestion.entity.Proveedor;
import com.gestion.repository.ProductoRepository;
import com.gestion.service.ProductoService;
import com.gestion.service.ProveedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

public class ProductoServiceTest {

    @InjectMocks
    private ProductoService productoService;  // Servicio a probar

    @Mock
    private ProductoRepository productoRepository;  // Mock del repositorio

    @Mock
    private ProveedorService proveedorService;  // Mock del servicio de proveedores

    private ProductoDTO productoDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializar los mocks
        productoDTO = new ProductoDTO(1L, "Producto 1", "Descripción", 100.0, 10, new Proveedor());
    }

    @Test
    public void testFindAll() {
        // Preparar el mock del repositorio
        when(productoRepository.findAll()).thenReturn(List.of(new Producto()));

        // Ejecutar el método
        List<ProductoDTO> productos = productoService.findAll();

        // Verificar que la lista no esté vacía
        assertNotNull(productos);
        assertFalse(productos.isEmpty());
    }

    @Test
    public void testFindById() {
        // Preparar el mock del repositorio
        Producto producto = new Producto();
        producto.setId(1L);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        // Ejecutar el método
        ProductoDTO result = productoService.findById(1L);

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(1L, result.getProducto_id());
    }

    @Test
    public void testSave() {
        // Preparar el mock del repositorio y del proveedorService
        when(proveedorService.save(Mockito.any(Proveedor.class))).thenReturn(new Proveedor());
        when(productoRepository.save(Mockito.any(Producto.class))).thenReturn(new Producto());

        // Ejecutar el método
        ProductoDTO savedProducto = productoService.save(productoDTO);

        // Verificar que el producto fue guardado
        assertNotNull(savedProducto);
    }

    @Test
    public void testUpdate() {
        // Preparar el mock del repositorio y del proveedorService
        Producto productoExistente = new Producto();
        productoExistente.setId(1L);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoExistente));
        when(proveedorService.save(Mockito.any(Proveedor.class))).thenReturn(new Proveedor());
        when(productoRepository.save(Mockito.any(Producto.class))).thenReturn(new Producto());

        // Ejecutar el método
        ProductoDTO updatedProducto = productoService.update(1L, productoDTO);

        // Verificar el resultado
        assertNotNull(updatedProducto);
        assertEquals(1L, updatedProducto.getProducto_id());
    }

    @Test
    public void testDelete() {
        // Preparar el mock del repositorio
        Mockito.doNothing().when(productoRepository).deleteById(1L);

        // Ejecutar el método
        productoService.delete(1L);

        // Verificar que no se haya lanzado ninguna excepción
        Mockito.verify(productoRepository, Mockito.times(1)).deleteById(1L);
    }
}
