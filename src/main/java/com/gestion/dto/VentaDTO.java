package com.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class VentaDTO {
   /* private Long id;
    private Long empleadoId; // ID del empleado que realiza la venta
    private ClienteDTO cliente; // Información del cliente (sin ID)
    private List<DetalleVentaDTO> detalles; // Lista de detalles de la venta
    private double subtotal; // Subtotal de la venta
    private double igv; // IGV de la venta
    private double total; // Total de la venta
    private LocalDateTime fecha; // Fecha de la venta*/
   private Long id;
    private Long empleadoId; // ID del empleado que realiza la venta
    private String empleadoNombre; // Nombre del empleado
    private String empleadoApellido; // Apellido del empleado
    private ClienteDTO cliente; // Información del cliente
    private List<DetalleVentaDTO> detalles; // Lista de detalles de la venta
    private double subtotal; // Subtotal de la venta
    private double igv; // IGV de la venta
    private double total; // Total de la venta
    private LocalDateTime fecha; // Fecha de la venta
}