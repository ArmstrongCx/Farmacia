package com.gestion.service;

import com.gestion.dto.ProductoMasVendidoDTO;
import com.gestion.repository.DetalleVentaRepository;
import com.gestion.repository.ProductoRepository; // Importa el ProductoRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private ProductoRepository productoRepository; // Inyecta el ProductoRepository

    public List<ProductoMasVendidoDTO> obtenerTop10ProductosMasVendidos() {
        return detalleVentaRepository.findTop10ProductosMasVendidos();
    }

    public int obtenerTotalProductosVendidos() {
        return detalleVentaRepository.findTotalProductosVendidos();
    }

    public Integer obtenerTotalCantidadProductos() {
        return productoRepository.findTotalCantidadProductos(); // Llama al método del ProductoRepository
    }
}