package com.ecommerce.geniusbar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.geniusbar.repository.ClienteRepository;
import com.ecommerce.geniusbar.model.Cliente;

@Service
public class ClienteServiceImpl implements ClienteService {

    //Inyeccion de dependencias del repositorio
    private final ClienteRepository clienteRepository;

    @Autowired
    private ClienteServiceImpl(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> todosClientes(){
        return clienteRepository.findAll();
    }

    public Optional<Cliente> encontrarCliente(Long id){
        return clienteRepository.findById(id);
    }

    public Cliente crearCliente(Cliente cliente){//PROBADO
        //Validacion para el DNI unico
        if(clienteRepository.findByDni(cliente.getDni()).stream().anyMatch(c -> !c.getId().equals(cliente.getId()))){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El DNI ya existe en la base de datos");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(Long id, Cliente cliente){//PROBADO
        Optional<Cliente> clienteOpcional = clienteRepository.findById(id);

        if(clienteOpcional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente con ID" + id + "no encontrado");
        }

        Cliente clienteExistente = clienteOpcional.get();
        
        // Validar si el DNI ya existe para otro cliente (si el DNI se actualiza)
        if(cliente.getDni() != null && !cliente.getDni().equals(clienteExistente.getDni())){
            if(clienteRepository.findByDni(cliente.getDni()).stream().anyMatch(c -> !c.getId().equals(id))){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un cliente con ese DNI");
            }
        }
        // Actualizar solo los campos que se permiten modificar del cliente existente
        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setCorreo(cliente.getCorreo());
        clienteExistente.setTelefono(cliente.getTelefono());
        clienteExistente.setDni(cliente.getDni());


        return clienteRepository.save(clienteExistente);

    }

    public void eliminarCliente(Long id){//PROBADO
        if(!clienteRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente con ID" + id + "no encontrado para eliminar");
        }
            clienteRepository.deleteById(id);
    }

    public Optional<Cliente> buscarPorDni(Long dni){
        return clienteRepository.findByDni(dni);
    }

    public List<Cliente> buscaraPorNombreCliente(String nombreCliente){
        return clienteRepository.findByNombreContainingIgnoreCase(nombreCliente);
    }

    public List<Cliente> buscarPorCorreo(String correo){
        return clienteRepository.findByCorreoContainingIgnoreCase(correo);
    }

    public List<Cliente> buscarPorTelefono(Long telefono){
        return clienteRepository.findByTelefono(telefono);
    }
}
