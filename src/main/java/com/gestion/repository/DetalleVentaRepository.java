package com.gestion.repository;

import com.gestion.dto.ProductoMasVendidoDTO;
import com.gestion.entity.DetalleVenta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleVentaRepository extends CrudRepository<DetalleVenta, Long> {

    @Query("SELECT SUM(d.cantidad) FROM DetalleVenta d")
    int findTotalProductosVendidos();

    @Query("SELECT new com.gestion.dto.ProductoMasVendidoDTO(d.producto.id, d.producto.nombre, SUM(d.cantidad)) " +
            "FROM DetalleVenta d GROUP BY d.producto.id, d.producto.nombre ORDER BY SUM(d.cantidad) DESC")
    List<ProductoMasVendidoDTO> findTop10ProductosMasVendidos();


}