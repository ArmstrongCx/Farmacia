package com.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenDTO {
    private Long id;
    private Long producto_id;
    private LocalDateTime fecha; // Asegúrate de que este campo esté presente
    private Long clienteId; // Asegúrate de que este campo esté presente
    private Long empleadoId; // Asegúrate de que este campo esté presente
    private List<DetalleVentaDTO> detalles; // Si es necesario incluir detalles

}