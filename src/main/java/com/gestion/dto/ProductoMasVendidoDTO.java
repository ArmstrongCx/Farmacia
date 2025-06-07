package com.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoMasVendidoDTO {
    private Long productoId;
    private String nombre;
    private Long cantidadVendida;
}
