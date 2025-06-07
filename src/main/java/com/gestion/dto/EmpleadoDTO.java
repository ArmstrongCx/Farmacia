package com.gestion.dto;


import com.gestion.entity.Acceso;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDTO {
    private Long empleado_id;
    private String nombre;
    private String apellido;
    private String dni;
    private String cargo;
    private Acceso acceso;
}