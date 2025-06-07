package com.gestion.repository;

import com.gestion.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {
    @Query("SELECT SUM(p.stock) FROM Producto p")
    Integer findTotalCantidadProductos();
}
