package com.gestion.repository;

import com.gestion.entity.Acceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccesoRepository extends JpaRepository<Acceso,Long> {
    Optional<Acceso> findByUsuario(String usuario);
}
