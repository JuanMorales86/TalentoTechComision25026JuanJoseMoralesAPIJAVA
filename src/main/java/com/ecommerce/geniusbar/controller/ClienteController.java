package com.ecommerce.geniusbar.controller;

import java.util.List;
import java.util.Optional;

import com.ecommerce.geniusbar.service.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.geniusbar.model.Cliente;
import com.ecommerce.geniusbar.service.ClienteService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/geniusbar/clientes")
public class ClienteController {

    private final ClienteServiceImpl clienteServiceImpl;

    private final ClienteService clienteService;


    @Autowired
    public ClienteController(ClienteService clienteService, ClienteServiceImpl clienteServiceImpl){
        this.clienteService = clienteService;
        this.clienteServiceImpl = clienteServiceImpl;
    }

    @GetMapping//PROBADO
    public List<Cliente> obtenerClientes(){
        return clienteService.todosClientes();
    }

    @GetMapping("/{id}")//PROBADO
    public ResponseEntity<Cliente> obtenerClientePodId(@PathVariable Long id){
        return clienteService.encontrarCliente(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping//PROBADO
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente){
        try{
            Cliente nuevoCliente = clienteService.crearCliente(cliente);
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);

        } catch(ResponseStatusException e){
            return ResponseEntity.status(e.getStatusCode()).body(null);
        }
    }

    @PutMapping("/{id}")//PROBADO
    public ResponseEntity<Cliente> actualizarClientes(@PathVariable Long id, @RequestBody Cliente cliente){
       try{
        Cliente clienteActualizado = clienteService.actualizarCliente(id, cliente);
        return ResponseEntity.ok(clienteActualizado);
       } catch(ResponseStatusException e){
        return ResponseEntity.status(e.getStatusCode()).body(null);
       }
    }

    @DeleteMapping("/{id}")//PROBADO
    public ResponseEntity<Void> eliminarClientes(@PathVariable Long id){
        try {
            clienteService.eliminarCliente(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    @GetMapping("/busqueda/{dni}")//PROBADO
    public ResponseEntity<Cliente> obtenerClientePorDNI(@PathVariable Long dni){
        // Aquí es donde cambia la lógica para manejar Optional
        Optional<Cliente> cliente = clienteService.buscarPorDni(dni);
        
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/busqueda/nombre/{nombreCliente}")//PROBADO
    public List<Cliente> obtenerClientePorNombre(@PathVariable String nombreCliente){
        return clienteService.buscaraPorNombreCliente(nombreCliente);
    }

    @GetMapping("/busqueda/correo/{correo}")//PROBADO
    public List<Cliente> obtenerClientePorCorreo(@PathVariable String correo){
        if(correo == null || correo.trim().isEmpty()){
            return clienteService.todosClientes();
        }

        return clienteService.buscarPorCorreo(correo);
    }

    @GetMapping("/busqueda/telefono/{telefono}")//PROBADO
    public List<Cliente> obtenerClientePorTelefono(@PathVariable Long telefono){
        if(telefono == null){
            return clienteService.todosClientes();
        }
        return clienteService.buscarPorTelefono(telefono);
    }


}
