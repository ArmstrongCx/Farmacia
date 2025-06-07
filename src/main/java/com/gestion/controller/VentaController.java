package com.gestion.controller;

import com.gestion.dto.VentaDTO;
import com.gestion.entity.Venta;
import com.gestion.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    /*@PostMapping("/registrar")
    public ResponseEntity<Venta> crearVenta(@RequestBody VentaDTO ventaDTO) {
        Venta nuevaVenta = ventaService.crearVenta(ventaDTO);



        //return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
        VentaDTO ventaResponse = ventaService.convertirAVentaDTO(nuevaVenta);

        return ResponseEntity.status(HttpStatus.CREATED).body(ventaResponse);

    }*/
    @PostMapping("/registrar")
    public ResponseEntity<VentaDTO> crearVenta(@RequestBody VentaDTO ventaDTO) {
        Venta nuevaVenta = ventaService.crearVenta(ventaDTO);

        // Convertir la entidad Venta a un DTO
        VentaDTO ventaResponse = ventaService.convertirAVentaDTO(nuevaVenta);

        return ResponseEntity.status(HttpStatus.CREATED).body(ventaResponse);
    }

}