package com.gestion.dto;

import com.gestion.entity.Proveedor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private Long producto_id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private Proveedor proveedor;
}