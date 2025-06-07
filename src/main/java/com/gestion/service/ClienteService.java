package com.gestion.service;

import com.gestion.dto.ClienteDTO;
import com.gestion.entity.Cliente;
import com.gestion.exception.ResourceNotFoundException;
import com.gestion.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> clientesDTO = new ArrayList<>();

        for (Cliente cliente : clientes) {
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setDni(cliente.getDni());
            clienteDTO.setNombre(cliente.getNombre());
            clienteDTO.setApellido(cliente.getApellido());
            clienteDTO.setEmail(cliente.getEmail());
            clienteDTO.setTelefono(cliente.getTelefono());

            clientesDTO.add(clienteDTO);
        }

        return clientesDTO;
    }

    public ClienteDTO getClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setDni(cliente.getDni());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setTelefono(cliente.getTelefono());

        return clienteDTO;
    }

    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setDni(clienteDTO.getDni());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());

        cliente = clienteRepository.save(cliente);

        clienteDTO.setDni(cliente.getDni());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setTelefono(cliente.getTelefono());

        return clienteDTO;
    }

    public ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        cliente.setDni(clienteDTO.getDni());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());

        cliente = clienteRepository.save(cliente);

        clienteDTO.setDni(cliente.getDni());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setTelefono(cliente.getTelefono());

        return clienteDTO;
    }
    public Cliente buscarPorDni(String dni) {
        return clienteRepository.findByDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con DNI: " + dni));
    }

    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}