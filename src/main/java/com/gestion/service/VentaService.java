package com.gestion.service;

import com.gestion.dto.ClienteDTO;
import com.gestion.dto.DetalleVentaDTO;
import com.gestion.dto.ProductoMasVendidoDTO;
import com.gestion.dto.VentaDTO;
import com.gestion.entity.Cliente;
import com.gestion.entity.DetalleVenta;
import com.gestion.entity.Empleado;
import com.gestion.entity.Producto;
import com.gestion.entity.Venta;
import com.gestion.exception.InsufficientStockException;
import com.gestion.exception.ResourceNotFoundException;
import com.gestion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ProductoRepository productoRepository;


    @Autowired
    private DetalleVentaRepository detalleVentaRepository;



    @Transactional
    public Venta crearVenta(VentaDTO ventaDTO) {
        // Validar que el cliente no sea nulo
        if (ventaDTO.getCliente() == null || ventaDTO.getCliente().getDni() == null) {
            throw new IllegalArgumentException("El cliente o su DNI no pueden ser nulos");
        }

        // Validar que el empleado no sea nulo
        if (ventaDTO.getEmpleadoId() == null) {
            throw new IllegalArgumentException("El ID del empleado no puede ser nulo");
        }

        // Validar que haya detalles de venta
        if (ventaDTO.getDetalles() == null || ventaDTO.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos un producto en la venta");
        }

        // Buscar cliente por DNI
        Cliente cliente = clienteRepository.findByDni(ventaDTO.getCliente().getDni())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con DNI: " + ventaDTO.getCliente().getDni()));

        // Buscar empleado por ID
        Empleado empleado = empleadoRepository.findById(ventaDTO.getEmpleadoId())
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con ID: " + ventaDTO.getEmpleadoId()));

        // Crear nueva venta
        Venta venta = new Venta();
        venta.setFecha(LocalDateTime.now());
        venta.setCliente(cliente);
        venta.setEmpleado(empleado);

        double subtotal = 0;
        List<DetalleVenta> detalles = new ArrayList<>();

        // Procesar cada detalle de venta
        for (DetalleVentaDTO detalleDTO : ventaDTO.getDetalles()) {
            if (detalleDTO.getProductoId() == null) {
                throw new IllegalArgumentException("El ID del producto no puede ser nulo");
            }

            Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + detalleDTO.getProductoId()));

            // Validar stock
            if (producto.getStock() < detalleDTO.getCantidad()) {
                throw new InsufficientStockException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Crear detalle de venta
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());

            // Calcular montos
            double subtotalDetalle = producto.getPrecio() * detalleDTO.getCantidad();
            detalle.setSubtotal(subtotalDetalle);
            detalle.setIgv(subtotalDetalle * 0.18);
            detalle.setTotal(subtotalDetalle * 1.18);

            subtotal += subtotalDetalle;
            detalles.add(detalle);

            // Actualizar stock del producto
            producto.setStock(producto.getStock() - detalleDTO.getCantidad());
            productoRepository.save(producto);
        }

        // Establecer totales de la venta
        venta.setSubtotal(subtotal);
        venta.setIgv(subtotal * 0.18);
        venta.setTotal(subtotal * 1.18);
        venta.setDetalles(detalles);

        // Guardar y devolver la venta
        return ventaRepository.save(venta);



    }
    public VentaDTO convertirAVentaDTO(Venta venta) {
        return new VentaDTO(
                venta.getId(),
                venta.getEmpleado().getEmpleado_id(),
                venta.getEmpleado().getNombre(), // Incluye el nombre del empleado
                venta.getEmpleado().getApellido(), // Incluye el apellido del empleado
                new ClienteDTO(
                        venta.getCliente().getDni(),
                        venta.getCliente().getNombre(),
                        venta.getCliente().getApellido(),
                        venta.getCliente().getEmail(),
                        venta.getCliente().getTelefono()
                ),
                venta.getDetalles().stream().map(detalle -> new DetalleVentaDTO(
                        detalle.getProducto().getId(),
                        detalle.getProducto().getNombre(), // Incluye el nombre del producto
                        detalle.getCantidad(),
                        detalle.getPrecioUnitario(),
                        detalle.getSubtotal(),
                        detalle.getIgv(),
                        detalle.getTotal()
                )).toList(),
                venta.getSubtotal(),
                venta.getIgv(),
                venta.getTotal(),
                venta.getFecha()
        );
    }










}