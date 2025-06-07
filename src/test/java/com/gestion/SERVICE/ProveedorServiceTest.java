package com.gestion.SERVICE;

import com.gestion.dto.ProveedorDTO;
import com.gestion.entity.Proveedor;
import com.gestion.exception.ProveedorNotFoundException;
import com.gestion.repository.ProveedorRepository;
import com.gestion.service.ProveedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    private Proveedor proveedor;
    private ProveedorDTO proveedorDTO;

    @BeforeEach
    void setUp() {
        proveedor = new Proveedor(1L, "Proveedor 1", "Teléfono", "RUC123", "email@proveedor.com", "http://url.imagen");
        proveedorDTO = new ProveedorDTO(1L, "Proveedor 1", "Teléfono", "RUC123", "email@proveedor.com", "http://url.imagen");
    }

    @Test
    void testSave() {
        // Configuramos el comportamiento del mock
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedor);

        // Llamamos al método de servicio
        Proveedor savedProveedor = proveedorService.save(proveedor);

        // Verificamos que el proveedor se guardó correctamente
        assertNotNull(savedProveedor);
        assertEquals("Proveedor 1", savedProveedor.getNombre());
        assertEquals("RUC123", savedProveedor.getRuc());
        assertEquals("email@proveedor.com", savedProveedor.getEmail());

        // Verificamos que el método del repositorio se haya llamado correctamente
        verify(proveedorRepository, times(1)).save(any(Proveedor.class));
    }

    @Test
    void testFindAll() {
        // Configuramos el comportamiento del mock
        when(proveedorRepository.findAll()).thenReturn(List.of(proveedor));

        // Llamamos al método de servicio
        List<ProveedorDTO> proveedores = proveedorService.findAll();

        // Verificamos que se haya devuelto la lista correcta
        assertNotNull(proveedores);
        assertEquals(1, proveedores.size());
        assertEquals("Proveedor 1", proveedores.get(0).getNombre());
    }

    @Test
    void testFindById_Existente() throws ProveedorNotFoundException {
        // Configuramos el comportamiento del mock
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));

        // Llamamos al método de servicio
        ProveedorDTO foundProveedor = proveedorService.findById(1L);

        // Verificamos que el proveedor se haya encontrado correctamente
        assertNotNull(foundProveedor);
        assertEquals("Proveedor 1", foundProveedor.getNombre());
        assertEquals("RUC123", foundProveedor.getRuc());
    }

    @Test
    void testFindById_NoExistente() {
        // Configuramos el comportamiento del mock
        when(proveedorRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificamos que se lance la excepción correcta
        assertThrows(ProveedorNotFoundException.class, () -> proveedorService.findById(1L));
        verify(proveedorRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdate_Existente() throws ProveedorNotFoundException {
        // Configuramos el comportamiento del mock
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedor);

        // Modificamos los datos del proveedor
        proveedor.setNombre("Proveedor Actualizado");

        // Llamamos al método de servicio
        ProveedorDTO updatedProveedor = proveedorService.update(1L, proveedor);

        // Verificamos que los datos se hayan actualizado correctamente
        assertNotNull(updatedProveedor);
        assertEquals("Proveedor Actualizado", updatedProveedor.getNombre());

        // Verificamos las interacciones con el repositorio
        verify(proveedorRepository, times(1)).save(any(Proveedor.class));
    }

    @Test
    void testDelete_Existente() throws ProveedorNotFoundException {
        // Configuramos el comportamiento del mock
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedor));
        doNothing().when(proveedorRepository).deleteById(1L);

        // Llamamos al método de servicio
        proveedorService.delete(1L);

        // Verificamos que el método de eliminación haya sido llamado correctamente
        verify(proveedorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NoExistente() {
        // Configuramos el comportamiento del mock para que no devuelva el proveedor
        when(proveedorRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificamos que se lance la excepción correcta cuando el proveedor no existe
        assertThrows(ProveedorNotFoundException.class, () -> proveedorService.delete(1L));

        // Verificamos que el método deleteById nunca haya sido llamado
        verify(proveedorRepository, never()).deleteById(1L);
    }



}
