package com.gestion.SERVICE;

import com.gestion.dto.AccesoDTO;
import com.gestion.entity.Acceso;
import com.gestion.repository.AccesoRepository;
import com.gestion.service.AccesoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccesoServiceTest {

    @Mock
    private AccesoRepository accesoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccesoService accesoService;

    private AccesoDTO accesoDTO;
    private Acceso acceso;

    @BeforeEach
    void setUp() {
        accesoDTO = new AccesoDTO();
        accesoDTO.setUsuario("juan123");
        accesoDTO.setPassword("clave123");

        acceso = new Acceso();
        acceso.setAccesoid(1L);
        acceso.setUsuario("juan123");
        acceso.setPassword("hashed123");
    }

    @Test
    void testCrearAcceso() {
        when(passwordEncoder.encode(accesoDTO.getPassword())).thenReturn("hashed123");
        when(accesoRepository.save(any(Acceso.class))).thenReturn(acceso);

        Acceso creado = accesoService.crearAcceso(accesoDTO);

        assertNotNull(creado);
        assertEquals("juan123", creado.getUsuario());
        verify(passwordEncoder, times(1)).encode("clave123");
        verify(accesoRepository, times(1)).save(any(Acceso.class));
    }

    @Test
    void testAutenticar_Exitoso() {
        when(accesoRepository.findByUsuario("juan123")).thenReturn(Optional.of(acceso));
        when(passwordEncoder.matches("clave123", "hashed123")).thenReturn(true);

        Optional<Acceso> resultado = accesoService.autenticar("juan123", "clave123");

        assertTrue(resultado.isPresent());
        assertEquals("juan123", resultado.get().getUsuario());
        verify(accesoRepository, times(1)).findByUsuario("juan123");
        verify(passwordEncoder, times(1)).matches("clave123", "hashed123");
    }

    @Test
    void testAutenticar_Fallido() {
        when(accesoRepository.findByUsuario("juan123")).thenReturn(Optional.of(acceso));
        when(passwordEncoder.matches("wrongpass", "hashed123")).thenReturn(false);

        Optional<Acceso> resultado = accesoService.autenticar("juan123", "wrongpass");

        assertFalse(resultado.isPresent());
        verify(accesoRepository, times(1)).findByUsuario("juan123");
        verify(passwordEncoder, times(1)).matches("wrongpass", "hashed123");
    }

    @Test
    void testCambiarContrasena_Exitoso() {
        when(accesoRepository.findByUsuario("juan123")).thenReturn(Optional.of(acceso));
        when(passwordEncoder.matches("clave123", "hashed123")).thenReturn(true);
        when(passwordEncoder.encode("nueva123")).thenReturn("nuevoHash");
        when(accesoRepository.save(any(Acceso.class))).thenReturn(acceso);

        boolean resultado = accesoService.cambiarContrasena("juan123", "clave123", "nueva123");

        assertTrue(resultado);
        verify(accesoRepository, times(1)).save(any(Acceso.class));
    }

    @Test
    void testCambiarContrasena_ContrasenaActualIncorrecta() {
        when(accesoRepository.findByUsuario("juan123")).thenReturn(Optional.of(acceso));
        when(passwordEncoder.matches("malaclave", "hashed123")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            accesoService.cambiarContrasena("juan123", "malaclave", "nueva123");
        });

        assertEquals("La contraseña actual es incorrecta", ex.getMessage());
        verify(accesoRepository, never()).save(any(Acceso.class));
    }

    @Test
    void testCambiarContrasena_UsuarioNoEncontrado() {
        when(accesoRepository.findByUsuario("desconocido")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            accesoService.cambiarContrasena("desconocido", "algo", "nuevo");
        });

        assertEquals("Usuario no encontrado", ex.getMessage());
        verify(accesoRepository, never()).save(any(Acceso.class));
    }
}
