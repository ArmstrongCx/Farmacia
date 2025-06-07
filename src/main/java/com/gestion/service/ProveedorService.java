package com.gestion.service;

import com.gestion.dto.ProveedorDTO;
import com.gestion.entity.Proveedor;
import com.gestion.exception.ProveedorNotFoundException;
import com.gestion.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public Proveedor save(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public List<ProveedorDTO> findAll() {
        return proveedorRepository.findAll().stream()
                .map(proveedor -> new ProveedorDTO(
                        proveedor.getId(),
                        proveedor.getNombre(),
                        proveedor.getTelefono(),
                        proveedor.getRuc(),
                        proveedor.getEmail(),
                        proveedor.getImagenUrl() // Asegúrate de incluir el campo imagenUrl
                ))
                .collect(Collectors.toList());
    }

    public ProveedorDTO findById(Long id) {
        Optional<Proveedor> proveedor = proveedorRepository.findById(id);
        return proveedor.map(p -> new ProveedorDTO(
                p.getId(),
                p.getNombre(),
                p.getTelefono(),
                p.getRuc(),
                p.getEmail(),
                p.getImagenUrl() // Asegúrate de incluir el campo imagenUrl
        )).orElse(null);
    }
    public ProveedorDTO update(Long id, Proveedor proveedor) {
        Optional<Proveedor> optionalProveedor = proveedorRepository.findById(id);
        if (optionalProveedor.isPresent()) {
            Proveedor existingProveedor = optionalProveedor.get();
            existingProveedor.setNombre(proveedor.getNombre());
            existingProveedor.setTelefono(proveedor.getTelefono());
            existingProveedor.setRuc(proveedor.getRuc());
            existingProveedor.setEmail(proveedor.getEmail());
            existingProveedor.setImagenUrl(proveedor.getImagenUrl());

            Proveedor updatedProveedor = proveedorRepository.save(existingProveedor);
            return new ProveedorDTO(
                    updatedProveedor.getId(),
                    updatedProveedor.getNombre(),
                    updatedProveedor.getTelefono(),
                    updatedProveedor.getRuc(),
                    updatedProveedor.getEmail(),
                    updatedProveedor.getImagenUrl()
            );
        }
        return null; // O lanza una excepción si no se encuentra el proveedor
    }

    public void delete(Long id) {
        Optional<Proveedor> proveedor = proveedorRepository.findById(id);
        if (!proveedor.isPresent()) {
            throw new ProveedorNotFoundException("Proveedor no encontrado");
        }
        proveedorRepository.deleteById(id);
    }

}