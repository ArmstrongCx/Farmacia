package com.gestion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Detalles_Venta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DetalleVentaID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "VentaID", referencedColumnName = "VentaID", nullable = false)
    private Venta venta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ProductoID", referencedColumnName = "ProductoId", nullable = false)
    private Producto producto;

    @Column(name = "Cantidad", nullable = false)
    private int cantidad;

    @Column(name = "PrecioUnitario", nullable = false)
    private double precioUnitario;

    @Column(name = "Subtotal", nullable = false)
    private double subtotal;

    @Column(name = "IGV", nullable = false)
    private double igv;

    @Column(name = "Total", nullable = false)
    private double total;
}