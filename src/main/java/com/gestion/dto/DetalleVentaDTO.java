package com.gestion.dto;

import com.gestion.entity.Producto;
import com.gestion.entity.Venta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentaDTO {
   /* private Long productoId;
    private Venta venta;
    private int cantidad;
    private double precio_unitario;
    private double subtotal;
    private double igv;
    private double total;*/
   private Long productoId;
    private String productoNombre; // Agrega este campo para el nombre del producto
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    private double igv;
    private double total;
}