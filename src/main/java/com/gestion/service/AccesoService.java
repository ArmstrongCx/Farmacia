package com.gestion.service;



import com.gestion.dto.AccesoDTO;
import com.gestion.entity.Acceso;
import com.gestion.repository.AccesoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccesoService {

    @Autowired
    private AccesoRepository accesoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Acceso crearAcceso(AccesoDTO accesoDTO) {
        Acceso acceso = new Acceso();
        acceso.setUsuario(accesoDTO.getUsuario());
        acceso.setPassword(passwordEncoder.encode(accesoDTO.getPassword())); // Encriptar la contraseña
        return accesoRepository.save(acceso);
    }

    public Optional<Acceso> autenticar(String usuario, String password) {
        Optional<Acceso> accesoOpt = accesoRepository.findByUsuario(usuario);
        if (accesoOpt.isPresent()) {
            Acceso acceso = accesoOpt.get();
            // Verificar la contraseña
            if (passwordEncoder.matches(password, acceso.getPassword())) {
                return Optional.of(acceso);
            }
        }
        return Optional.empty();
    }
    public boolean cambiarContrasena(String usuario, String contrasenaActual, String nuevaContrasena) {
        Optional<Acceso> accesoOpt = accesoRepository.findByUsuario(usuario);
        if (accesoOpt.isPresent()) {
            Acceso acceso = accesoOpt.get();
            // Verificar la contraseña actual
            if (passwordEncoder.matches(contrasenaActual, acceso.getPassword())) {

                acceso.setPassword(passwordEncoder.encode(nuevaContrasena));
                accesoRepository.save(acceso); // Guardar el acceso actualizado
                return true; // Contraseña cambiada con éxito
            } else {
                throw new RuntimeException("La contraseña actual es incorrecta");
            }
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }
}