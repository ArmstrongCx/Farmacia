package com.gestion.SERVICE;


import com.gestion.dto.ClienteDTO;
import com.gestion.entity.Cliente;
import com.gestion.exception.ResourceNotFoundException;
import com.gestion.repository.ClienteRepository;
import com.gestion.service.ClienteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setDni("12345678");
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
        cliente.setEmail("juan.perez@example.com");
        cliente.setTelefono("999999999");

        clienteDTO = new ClienteDTO();
        clienteDTO.setDni("12345678");
        clienteDTO.setNombre("Juan");
        clienteDTO.setApellido("Pérez");
        clienteDTO.setEmail("juan.perez@example.com");
        clienteDTO.setTelefono("999999999");
    }

    @Test
    void testGetAllClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<ClienteDTO> resultado = clienteService.getAllClientes();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testGetClienteById_Existente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        ClienteDTO resultado = clienteService.getClienteById(1L);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void testGetClienteById_NoExistente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.getClienteById(1L));
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateCliente() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDTO creado = clienteService.createCliente(clienteDTO);

        assertNotNull(creado);
        assertEquals("Juan", creado.getNombre());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testUpdateCliente_Existente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDTO actualizado = clienteService.updateCliente(1L, clienteDTO);

        assertNotNull(actualizado);
        assertEquals("Juan", actualizado.getNombre());
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testUpdateCliente_NoExistente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.updateCliente(1L, clienteDTO));
        verify(clienteRepository, times(1)).findById(1L);
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void testBuscarPorDni_Existente() {
        when(clienteRepository.findByDni("12345678")).thenReturn(Optional.of(cliente));

        Cliente resultado = clienteService.buscarPorDni("12345678");

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(clienteRepository, times(1)).findByDni("12345678");
    }

    @Test
    void testBuscarPorDni_NoExistente() {
        when(clienteRepository.findByDni("12345678")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clienteService.buscarPorDni("12345678"));
        verify(clienteRepository, times(1)).findByDni("12345678");
    }

    @Test
    void testDeleteCliente() {
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.deleteCliente(1L);

        verify(clienteRepository, times(1)).deleteById(1L);
    }
}
