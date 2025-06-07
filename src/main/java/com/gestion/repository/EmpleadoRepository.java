package com.gestion.repository;

import com.gestion.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado,Long> {
    Empleado findByDni(String dni);
    Empleado findByAccesoUsuarioAndAccesoPassword(String usuario, String password);
}
