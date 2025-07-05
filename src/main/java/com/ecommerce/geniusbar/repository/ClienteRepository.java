package com.ecommerce.geniusbar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.geniusbar.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    //Metodos automaticos


    //Otros Personalizados
    Optional<Cliente> findByDni(Long dni);
    List<Cliente> findByNombreContainingIgnoreCase(String nombreCliente);// Buscar clientes por nombre, ignorando mayusculas y minusculas
    List<Cliente> findByCorreoContainingIgnoreCase(String correo);
    List<Cliente> findByTelefono(Long telefono);

    
}
