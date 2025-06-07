package com.gestion.service;

import com.gestion.dto.EmpleadoDTO;
import com.gestion.entity.Empleado;
import com.gestion.entity.Acceso;
import com.gestion.repository.EmpleadoRepository;
import com.gestion.repository.AccesoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private AccesoRepository accesoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyecta el PasswordEncoder

    public Optional<EmpleadoDTO> autenticarEmpleado(String usuario, String password) {
        Optional<Acceso> accesoOpt = accesoRepository.findByUsuario(usuario);
        if (accesoOpt.isPresent()) {
            Acceso acceso = accesoOpt.get();
            // Verificar la contraseña
            if (passwordEncoder.matches(password, acceso.getPassword())) {
                // Si se encuentra el acceso, buscar el empleado asociado
                Optional<Empleado> empleadoOpt = empleadoRepository.findById(acceso.getAccesoid());
                return empleadoOpt.map(this::convertToDTO);
            }
        }
        return Optional.empty(); // No se encontró el acceso o la contraseña es incorrecta
    }

    // Convertir Empleado a EmpleadoDTO
    private EmpleadoDTO convertToDTO(Empleado empleado) {
        return new EmpleadoDTO(empleado.getEmpleado_id(), empleado.getNombre(), empleado.getApellido(), empleado.getDni(), empleado.getCargo(), empleado.getAcceso());
    }

    // Convertir EmpleadoDTO a Empleado
    private Empleado convertToEntity(EmpleadoDTO empleadoDTO) {
        return new Empleado(empleadoDTO.getEmpleado_id(), empleadoDTO.getNombre(), empleadoDTO.getApellido(), empleadoDTO.getDni(), empleadoDTO.getCargo(), empleadoDTO.getAcceso());
    }

    public List<EmpleadoDTO> obtenerTodosLosEmpleados() {
        List<Empleado> empleados = empleadoRepository.findAll();
        return empleados.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<EmpleadoDTO> obtenerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id).map(this::convertToDTO);
    }

    public EmpleadoDTO crearEmpleado(EmpleadoDTO empleadoDTO) {
        // Crear el acceso antes de crear el empleado
        Acceso acceso = new Acceso();
        acceso.setUsuario(empleadoDTO.getAcceso().getUsuario());
        acceso.setPassword(passwordEncoder.encode(empleadoDTO.getAcceso().getPassword())); // Encriptar la contraseña

        // Guardar el acceso
        Acceso accesoGuardado = accesoRepository.save(acceso);
        empleadoDTO.setAcceso(accesoGuardado); // Asignar el acceso guardado al empleado

        Empleado empleado = convertToEntity(empleadoDTO);
        Empleado empleadoGuardado = empleadoRepository.save(empleado);
        return convertToDTO(empleadoGuardado);
    }

    public EmpleadoDTO actualizarEmpleado(Long id, EmpleadoDTO empleadoDTO) {
        if (empleadoRepository.existsById(id)) {
            // Obtener el empleado existente
            Empleado empleadoExistente = empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

            empleadoExistente.setNombre(empleadoDTO.getNombre());
            empleadoExistente.setApellido(empleadoDTO.getApellido());
            empleadoExistente.setDni(empleadoDTO.getDni());
            empleadoExistente.setCargo(empleadoDTO.getCargo());

            Acceso accesoExistente = empleadoExistente.getAcceso();
            if (accesoExistente != null) {
                accesoExistente.setUsuario(empleadoDTO.getAcceso().getUsuario());
                accesoExistente.setPassword(passwordEncoder.encode(empleadoDTO.getAcceso().getPassword())); // Encriptar la nueva contraseña
            }

            Empleado empleadoActualizado = empleadoRepository.save(empleadoExistente);
            return convertToDTO(empleadoActualizado);
        }
        return null; // O lanzar una excepción
    }

    public void eliminarEmpleado(Long id) {
        if (empleadoRepository.existsById(id)) {
            // Obtener el empleado existente
            Empleado empleadoExistente = empleadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

            // Obtener el acceso asociado
            Acceso accesoExistente = empleadoExistente.getAcceso();
            if (accesoExistente != null) {
                // Eliminar el acceso
                accesoRepository.delete(accesoExistente);
            }

            // Eliminar el empleado
            empleadoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Empleado no encontrado");
        }
    }

}