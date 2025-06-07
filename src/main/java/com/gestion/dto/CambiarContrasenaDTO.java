package com.gestion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CambiarContrasenaDTO {
    private String usuario;
    private String contrasenaActual;
    private String nuevaContrasena;
}