package com.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductoVentaDTO {
    private Long id; // ID del producto
    private Integer cantidad; // Cantidad del producto
    private Double precio; // Precio unitario del producto
    private Double subtotal; // Subtotal para esta cantidad
    private Double igv; // IGV calculado
    private Double total; // Total con IGV


}