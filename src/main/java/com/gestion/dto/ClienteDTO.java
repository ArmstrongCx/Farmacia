package com.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
}