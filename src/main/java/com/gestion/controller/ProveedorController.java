package com.gestion.controller;

import com.gestion.dto.ProveedorDTO;
import com.gestion.entity.Proveedor;
import com.gestion.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    // Método para crear un nuevo proveedor
    @PostMapping
    public ResponseEntity<ProveedorDTO> createProveedor(@RequestBody ProveedorDTO proveedorDTO) {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(proveedorDTO.getNombre());
        proveedor.setTelefono(proveedorDTO.getTelefono());
        proveedor.setRuc(proveedorDTO.getRuc());
        proveedor.setEmail(proveedorDTO.getEmail());
        proveedor.setImagenUrl(proveedorDTO.getImagenUrl());

        Proveedor savedProveedor = proveedorService.save(proveedor);
        ProveedorDTO savedProveedorDTO = new ProveedorDTO(
                savedProveedor.getId(),
                savedProveedor.getNombre(),
                savedProveedor.getTelefono(),
                savedProveedor.getRuc(),
                savedProveedor.getEmail(),
                savedProveedor.getImagenUrl()
        );

        return new ResponseEntity<>(savedProveedorDTO, HttpStatus.CREATED);
    }

    // Método para obtener todos los proveedores
    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> getAllProveedores() {
        List<ProveedorDTO> proveedores = proveedorService.findAll();
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }

    // Método para obtener un proveedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> getProveedorById(@PathVariable Long id) {
        ProveedorDTO proveedor = proveedorService.findById(id);
        if (proveedor != null) {
            return new ResponseEntity<>(proveedor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Método para actualizar un proveedor existente
    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> updateProveedor(@PathVariable Long id, @RequestBody ProveedorDTO proveedorDTO) {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(proveedorDTO.getNombre());
        proveedor.setTelefono(proveedorDTO.getTelefono());
        proveedor.setRuc(proveedorDTO.getRuc());
        proveedor.setEmail(proveedorDTO.getEmail());
        proveedor.setImagenUrl(proveedorDTO.getImagenUrl());

        ProveedorDTO updatedProveedor = proveedorService.update(id, proveedor);
        if (updatedProveedor != null) {
            return new ResponseEntity<>(updatedProveedor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Método para eliminar un proveedor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        proveedorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}