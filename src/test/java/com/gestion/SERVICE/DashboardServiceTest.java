package com.gestion.SERVICE;

import com.gestion.dto.ProductoMasVendidoDTO;
import com.gestion.repository.DetalleVentaRepository;
import com.gestion.repository.ProductoRepository; // Importa el ProductoRepository
import com.gestion.service.DashboardService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private DetalleVentaRepository detalleVentaRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @BeforeEach
    void setUp() {
        // Aquí no es necesario hacer cambios, ya que no afecta a la prueba
    }

    @Test
    void testObtenerTop10ProductosMasVendidos() {
        ProductoMasVendidoDTO producto1 = new ProductoMasVendidoDTO(1L, "Producto 1", 10L); // Usar Long
        ProductoMasVendidoDTO producto2 = new ProductoMasVendidoDTO(2L, "Producto 2", 8L);  // Usar Long
        List<ProductoMasVendidoDTO> productos = List.of(producto1, producto2);

        when(detalleVentaRepository.findTop10ProductosMasVendidos()).thenReturn(productos);

        List<ProductoMasVendidoDTO> resultado = dashboardService.obtenerTop10ProductosMasVendidos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Producto 1", resultado.get(0).getNombre());
        assertEquals(10L, resultado.get(0).getCantidadVendida());  // Usar Long en la comparación
        verify(detalleVentaRepository, times(1)).findTop10ProductosMasVendidos();
    }

    @Test
    void testObtenerTotalProductosVendidos() {
        when(detalleVentaRepository.findTotalProductosVendidos()).thenReturn(100);

        int resultado = dashboardService.obtenerTotalProductosVendidos();

        assertEquals(100, resultado);
        verify(detalleVentaRepository, times(1)).findTotalProductosVendidos();
    }

    @Test
    void testObtenerTotalCantidadProductos() {
        when(productoRepository.findTotalCantidadProductos()).thenReturn(500);

        Integer resultado = dashboardService.obtenerTotalCantidadProductos();

        assertEquals(500, resultado);
        verify(productoRepository, times(1)).findTotalCantidadProductos();
    }
}
