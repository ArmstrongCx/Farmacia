package com.gestion.controller;

import com.gestion.dto.EmpleadoDTO;


import com.gestion.dto.LoginRequest;
import com.gestion.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    // Método para registrar un nuevo empleado
    @PostMapping("/register")
    public ResponseEntity<EmpleadoDTO> registrarEmpleado(@RequestBody EmpleadoDTO empleadoDTO) {
        EmpleadoDTO nuevoEmpleado = empleadoService.crearEmpleado(empleadoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
    }
    @PostMapping("/login")
    public ResponseEntity<EmpleadoDTO> autenticarEmpleado(@RequestBody LoginRequest loginRequest) {
        Optional<EmpleadoDTO> empleadoOpt = empleadoService.autenticarEmpleado(loginRequest.getUsuario(), loginRequest.getPassword());
        return empleadoOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }


    // Método para obtener todos los empleados
    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> obtenerTodosLosEmpleados() {
        List<EmpleadoDTO> empleados = empleadoService.obtenerTodosLosEmpleados();
        return ResponseEntity.ok(empleados);
    }

    // Método para obtener un empleado por ID
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> obtenerEmpleadoPorId(@PathVariable Long id) {
        return empleadoService.obtenerEmpleadoPorId(id)
                .map(empleadoDTO -> ResponseEntity.ok(empleadoDTO))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Método para actualizar un empleado
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> actualizarEmpleado(@PathVariable Long id, @RequestBody EmpleadoDTO empleadoDTO) {
        EmpleadoDTO empleadoActualizado = empleadoService.actualizarEmpleado(id, empleadoDTO);
        return empleadoActualizado != null ? ResponseEntity.ok(empleadoActualizado) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Método para eliminar un empleado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }


}