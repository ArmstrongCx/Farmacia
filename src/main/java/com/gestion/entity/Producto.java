package com.gestion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ProductoId",nullable = false)
    private Long id;

    @Column(name="Nombre")
    private String nombre;

    @Column(name="Descripcion")
    private String descripcion;

    @Column(name="Precio")
    private double precio;

    @Column(name="Stock")
    private int stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ProveedorId",referencedColumnName = "ProveedorID")
    private Proveedor proveedor;
}
