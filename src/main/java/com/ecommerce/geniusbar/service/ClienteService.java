package com.ecommerce.geniusbar.service;

import java.util.List;
import java.util.Optional;

import com.ecommerce.geniusbar.model.Cliente;

public interface ClienteService {

    List<Cliente> todosClientes();
    Optional<Cliente> encontrarCliente(Long id);
    Cliente crearCliente(Cliente cliente);
    Cliente actualizarCliente(Long id, Cliente cliente);
    void eliminarCliente(Long id);

    Optional<Cliente> buscarPorDni(Long dni);

    List<Cliente> buscaraPorNombreCliente(String nombreCliente);
    List<Cliente> buscarPorCorreo(String correo);
    List<Cliente> buscarPorTelefono(Long telefono);
}
