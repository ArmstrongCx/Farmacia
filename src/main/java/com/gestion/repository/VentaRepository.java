package com.gestion.repository;

import com.gestion.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface VentaRepository extends JpaRepository<Venta ,Long> {

}
