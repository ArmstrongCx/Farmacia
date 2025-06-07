package com.gestion.controller;

import com.gestion.dto.ProductoMasVendidoDTO;
import com.gestion.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/top-productos")
    public List<ProductoMasVendidoDTO> obtenerTop10ProductosMasVendidos() {
        return dashboardService.obtenerTop10ProductosMasVendidos();
    }

    @GetMapping("/total-productos-vendidos")
    public int obtenerTotalProductosVendidos() {
        return dashboardService.obtenerTotalProductosVendidos();
    }

    @GetMapping("/total-productos-inventario")
    public Integer obtenerTotalCantidadProductos() {
        return dashboardService.obtenerTotalCantidadProductos(); // Llama al método del servicio
    }
}