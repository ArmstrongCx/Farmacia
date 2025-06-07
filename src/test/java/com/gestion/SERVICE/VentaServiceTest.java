package com.gestion.SERVICE;

import com.gestion.dto.ClienteDTO;
import com.gestion.dto.DetalleVentaDTO;
import com.gestion.dto.VentaDTO;
import com.gestion.entity.Cliente;
import com.gestion.entity.Empleado;
import com.gestion.entity.Producto;
import com.gestion.entity.Venta;
import com.gestion.exception.InsufficientStockException;
import com.gestion.exception.ResourceNotFoundException;
import com.gestion.repository.ClienteRepository;
import com.gestion.repository.EmpleadoRepository;
import com.gestion.repository.ProductoRepository;
import com.gestion.repository.VentaRepository;
import com.gestion.service.VentaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private VentaService ventaService;

    private Cliente cliente;
    private Empleado empleado;
    private Producto producto;
    private VentaDTO ventaDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear datos de prueba
        cliente = new Cliente();
        cliente.setDni("12345678");
        cliente.setNombre("Juan");
        cliente.setApellido("Perez");

        empleado = new Empleado();
        empleado.setEmpleado_id(1L);
        empleado.setNombre("Carlos");
        empleado.setApellido("Gomez");

        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto A");
        producto.setPrecio(50.0);
        producto.setStock(10);

        // Crear un DTO de prueba
        DetalleVentaDTO detalle = new DetalleVentaDTO(1L, "Producto A", 2, 50.0, 100.0, 18.0, 118.0);
        ventaDTO = new VentaDTO(1L, 1L, "Carlos", "Gomez", new ClienteDTO("12345678", "Juan", "Perez", "juan@example.com", "123456789"), Arrays.asList(detalle), 100.0, 18.0, 118.0, LocalDateTime.now());
    }

    @Test
    void testCrearVenta() {
        // Simulamos que el cliente y el empleado existen en la base de datos
        when(clienteRepository.findByDni("12345678")).thenReturn(Optional.of(cliente));
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));

        // Simulamos que el producto existe en la base de datos
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        // Ejecutar el método
        Venta venta = ventaService.crearVenta(ventaDTO);

        // Verificar el resultado
        assertNotNull(venta);
        assertEquals(venta.getCliente(), cliente);
        assertEquals(venta.getEmpleado(), empleado);
        assertEquals(venta.getDetalles().size(), 1);
        assertEquals(venta.getSubtotal(), 100.0);
        assertEquals(venta.getIgv(), 18.0);
        assertEquals(venta.getTotal(), 118.0);

        // Verificar que el stock del producto se ha reducido
        verify(productoRepository).save(producto);
        assertEquals(producto.getStock(), 8);
    }

    @Test
    void testCrearVenta_ProductoNoDisponible() {
        // Simulamos que el producto no tiene suficiente stock
        producto.setStock(1); // Producto con solo 1 en stock

        // Simulamos que el cliente y el empleado existen en la base de datos
        when(clienteRepository.findByDni("12345678")).thenReturn(Optional.of(cliente));
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));

        // Simulamos que el producto existe en la base de datos
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        // Intentar crear una venta con cantidad mayor al stock disponible
        DetalleVentaDTO detalle = new DetalleVentaDTO(1L, "Producto A", 2, 50.0, 100.0, 18.0, 118.0);
        ventaDTO.setDetalles(Arrays.asList(detalle));

        // Ejecutar el método y verificar que lanza la excepción
        assertThrows(InsufficientStockException.class, () -> ventaService.crearVenta(ventaDTO));
    }

    @Test
    void testCrearVenta_ClienteNoEncontrado() {
        // Simulamos que el cliente no se encuentra en la base de datos
        when(clienteRepository.findByDni("12345678")).thenReturn(Optional.empty());

        // Ejecutar el método y verificar que lanza la excepción
        assertThrows(ResourceNotFoundException.class, () -> ventaService.crearVenta(ventaDTO));
    }

    @Test
    void testCrearVenta_EmpleadoNoEncontrado() {
        // Simulamos que el empleado no se encuentra en la base de datos
        when(clienteRepository.findByDni("12345678")).thenReturn(Optional.of(cliente));
        when(empleadoRepository.findById(1L)).thenReturn(Optional.empty());

        // Ejecutar el método y verificar que lanza la excepción
        assertThrows(ResourceNotFoundException.class, () -> ventaService.crearVenta(ventaDTO));
    }
}
