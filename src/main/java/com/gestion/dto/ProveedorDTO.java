package com.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorDTO {
    private Long id;
    private String nombre;
    private String telefono;
    private String ruc;
    private String email;
    private String imagenUrl;

}