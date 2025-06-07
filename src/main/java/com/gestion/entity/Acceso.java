package com.gestion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Accesos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Acceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="AccesoId",nullable = false)
    private Long accesoid;

    @Column(name="Usuario",nullable = false)
    private String usuario;

    @Column(name="Password",nullable = false)
    private String password;


}
