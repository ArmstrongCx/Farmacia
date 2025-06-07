package com.gestion.controller;

import com.gestion.dto.AccesoDTO;
import com.gestion.dto.CambiarContrasenaDTO;

import com.gestion.entity.Acceso;
import com.gestion.service.AccesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/accesos")
public class AccesoController {

    @Autowired
    private AccesoService accesoService;

    @PostMapping
    public ResponseEntity<Acceso> crearAcceso(@RequestBody AccesoDTO accesoDTO) {
        Acceso nuevoAcceso = accesoService.crearAcceso(accesoDTO);
        return ResponseEntity.ok(nuevoAcceso);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AccesoDTO accesoDTO) {
        Optional<Acceso> accesoOpt = accesoService.autenticar(accesoDTO.getUsuario(), accesoDTO.getPassword());
        if (accesoOpt.isPresent()) {
            return ResponseEntity.ok(accesoOpt.get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }

    @PostMapping("/cambiar-contrasena")
    public ResponseEntity<String> cambiarContrasena(@RequestBody CambiarContrasenaDTO request) {
        try {
            boolean exito = accesoService.cambiarContrasena(request.getUsuario(), request.getContrasenaActual(), request.getNuevaContrasena());
            if (exito) {
                return ResponseEntity.ok("Contraseña cambiada con éxito");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo cambiar la contraseña");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}