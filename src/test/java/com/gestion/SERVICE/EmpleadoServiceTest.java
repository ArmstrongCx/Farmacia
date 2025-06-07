package com.gestion.SERVICE;

import com.gestion.dto.EmpleadoDTO;
import com.gestion.entity.Acceso;
import com.gestion.entity.Empleado;
import com.gestion.repository.AccesoRepository;
import com.gestion.repository.EmpleadoRepository;
import com.gestion.service.EmpleadoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmpleadoServiceTest {

    @InjectMocks
    private EmpleadoService empleadoService;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private AccesoRepository accesoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAutenticarEmpleado_Success() {
        String usuario = "admin";
        String password = "12345";
        String encodedPassword = "$2a$10$hashed";

        Acceso acceso = new Acceso(1L, usuario, encodedPassword);
        Empleado empleado = new Empleado(1L, "Juan", "Perez", "12345678", "Veterinario", acceso);

        when(accesoRepository.findByUsuario(usuario)).thenReturn(Optional.of(acceso));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));

        Optional<EmpleadoDTO> resultado = empleadoService.autenticarEmpleado(usuario, password);

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
    }

    @Test
    void testAutenticarEmpleado_PasswordIncorrecta() {
        String usuario = "admin";
        String password = "wrong";

        Acceso acceso = new Acceso(1L, usuario, "$2a$10$hashed");

        when(accesoRepository.findByUsuario(usuario)).thenReturn(Optional.of(acceso));
        when(passwordEncoder.matches(password, acceso.getPassword())).thenReturn(false);

        Optional<EmpleadoDTO> resultado = empleadoService.autenticarEmpleado(usuario, password);

        assertFalse(resultado.isPresent());
    }

    @Test
    void testCrearEmpleado() {
        Acceso acceso = new Acceso(null, "usuarioNuevo", "clave123");
        EmpleadoDTO dto = new EmpleadoDTO(null, "Lucía", "Ramirez", "87654321", "Asistente", acceso);

        Acceso accesoGuardado = new Acceso(1L, "usuarioNuevo", "claveEncriptada");
        Empleado empleado = new Empleado(null, "Lucía", "Ramirez", "87654321", "Asistente", accesoGuardado);
        Empleado empleadoGuardado = new Empleado(1L, "Lucía", "Ramirez", "87654321", "Asistente", accesoGuardado);

        when(passwordEncoder.encode("clave123")).thenReturn("claveEncriptada");
        when(accesoRepository.save(any(Acceso.class))).thenReturn(accesoGuardado);
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleadoGuardado);

        EmpleadoDTO resultado = empleadoService.crearEmpleado(dto);

        assertNotNull(resultado);
        assertEquals("Lucía", resultado.getNombre());
        assertEquals("usuarioNuevo", resultado.getAcceso().getUsuario());
    }

    @Test
    void testObtenerTodosLosEmpleados() {
        List<Empleado> lista = List.of(
                new Empleado(1L, "Ana", "López", "11111111", "Recepcionista", null),
                new Empleado(2L, "Carlos", "Torres", "22222222", "Veterinario", null)
        );

        when(empleadoRepository.findAll()).thenReturn(lista);

        List<EmpleadoDTO> resultado = empleadoService.obtenerTodosLosEmpleados();

        assertEquals(2, resultado.size());
        assertEquals("Ana", resultado.get(0).getNombre());
    }

    @Test
    void testObtenerEmpleadoPorId_Encontrado() {
        Empleado empleado = new Empleado(1L, "Mario", "Gómez", "33333333", "Técnico", null);
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));

        Optional<EmpleadoDTO> resultado = empleadoService.obtenerEmpleadoPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Mario", resultado.get().getNombre());
    }

    @Test
    void testActualizarEmpleado() {
        Long id = 1L;
        Acceso acceso = new Acceso(1L, "usuario", "oldpass");
        Empleado existente = new Empleado(id, "Luis", "Viejo", "00000000", "Antiguo", acceso);

        Acceso nuevoAcceso = new Acceso(1L, "nuevoUsuario", "nuevaClave");
        EmpleadoDTO dto = new EmpleadoDTO(id, "Luis", "Nuevo", "99999999", "NuevoCargo", nuevoAcceso);

        when(empleadoRepository.existsById(id)).thenReturn(true);
        when(empleadoRepository.findById(id)).thenReturn(Optional.of(existente));
        when(passwordEncoder.encode("nuevaClave")).thenReturn("claveHasheada");
        when(empleadoRepository.save(any(Empleado.class))).thenAnswer(i -> i.getArgument(0));

        EmpleadoDTO resultado = empleadoService.actualizarEmpleado(id, dto);

        assertEquals("Luis", resultado.getNombre());
        assertEquals("nuevoUsuario", resultado.getAcceso().getUsuario());
        assertEquals("claveHasheada", resultado.getAcceso().getPassword());
    }

    @Test
    void testEliminarEmpleado() {
        Long id = 1L;
        Acceso acceso = new Acceso(2L, "deleteUser", "clave");
        Empleado empleado = new Empleado(id, "Borrar", "Empleado", "77777777", "Cargo", acceso);

        when(empleadoRepository.existsById(id)).thenReturn(true);
        when(empleadoRepository.findById(id)).thenReturn(Optional.of(empleado));

        empleadoService.eliminarEmpleado(id);

        verify(accesoRepository, times(1)).delete(acceso);
        verify(empleadoRepository, times(1)).deleteById(id);
    }
}
