package com.gestion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Proveedores",uniqueConstraints = @UniqueConstraint(columnNames = "Ruc"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ProveedorID",nullable = false)
    private Long id;

    @Column(name="Nombre")
    private String nombre;

    @Column(name="Telefono")
    private String telefono;

    @Column(name="Ruc")
    private String ruc;

    @Column(name="Email")
    private String email;

    @Column(name="Url") // Nuevo campo para la URL
    private String imagenUrl;


}
