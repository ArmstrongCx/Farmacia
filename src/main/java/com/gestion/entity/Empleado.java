package com.gestion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name="Empleados",uniqueConstraints = @UniqueConstraint(columnNames = "Dni"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name="EmpleadoID",nullable = false)
    private Long empleado_id;

    @Column(name="Nombre")
    private String nombre;

    @Column(name="Apellido")
    private String apellido;

    @Column(name="Dni")
    private String dni;

    @Column(name="Cargo")
    private String cargo;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="accesoId",referencedColumnName = "AccesoId")
    private Acceso acceso;
}
