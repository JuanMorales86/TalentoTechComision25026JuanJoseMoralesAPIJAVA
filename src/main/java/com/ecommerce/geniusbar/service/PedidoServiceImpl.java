package com.ecommerce.geniusbar.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.geniusbar.repository.PedidoRepository;
import com.ecommerce.geniusbar.model.Cliente;
import com.ecommerce.geniusbar.model.DetallePedido;
import com.ecommerce.geniusbar.model.Pedidos;

@Service
public class PedidoServiceImpl implements PedidoService {

    //Inyeccion de dependencias del repositorio
    private final PedidoRepository pedidoRepository;//inicializamos 
    private final ClienteService clienteService;//inicializamos 

    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository, ClienteService clienteService){
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
    }

    public List<Pedidos> todosPedidos(){
        return pedidoRepository.findAll();
    }

    public Optional<Pedidos> encontrarPedido(Long id){
        return pedidoRepository.findById(id);
    }

    
    public Pedidos crearPedido(Pedidos pedido, Long idCliente){
        //buscar el cliente por id
        Optional<Cliente> clienteOpcional = clienteService.encontrarCliente(idCliente);

        if(clienteOpcional.isEmpty()){//Verificar si el cliente existe
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente con ID" + idCliente + "no encontrado");
        }
        //asignar el objeto Cliente encontrado al pedido
        pedido.setCliente(clienteOpcional.get());
        
        if (pedido.getDetalles() != null) {
        for (DetallePedido detalle : pedido.getDetalles()) {
            detalle.setPedido(pedido);
            }
        }
        //guardar el pedido
        return pedidoRepository.save(pedido);
    }


    public Pedidos actualizarPedido(Long id, Pedidos pedido){
        // Primero, verificar si el pedido existe
        Optional<Pedidos> pedidoExiste = pedidoRepository.findById(id);
        if(pedidoExiste.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido con ID" + id + "no encontrado");
        }

        // Recuperar el cliente existente del pedido para no perder la relación si no se envía en el PUT
        Cliente clienteExistente = pedidoExiste.get().getCliente();
        
        // Si el cliente del pedido que se recibe es nulo, mantenemos el cliente existente.
        // Si se envía un cliente, asumimos que se quiere actualizar la relación.
        // Aquí podríamos añadir lógica para buscar un nuevo cliente si se envía un id de cliente distinto (me falta)
        if(pedido.getCliente() != null && pedido.getCliente().getId() !=null){
            Optional<Cliente> nuevoClienteOpcional = clienteService.encontrarCliente(pedido.getCliente().getId());
            if(nuevoClienteOpcional.isPresent()){
                pedido.setCliente(nuevoClienteOpcional.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente con ID" + pedido.getCliente().getId() + "no encontrado para actualizar el pedido");
            }

        } else {
            pedido.setCliente(clienteExistente); // Mantener el cliente si no se proporciona uno
        }
        pedido.setId(id);// Actualizar el ID del pedido
        return pedidoRepository.save(pedido);// Guardar el pedido actualizado
    }

    public void eliminarPedido(Long id){
        pedidoRepository.deleteById(id);
    }

    public List<Pedidos> buscarPorEstadoPedido(String estadoPedido){
        return pedidoRepository.findByEstadoPedido(estadoPedido);
    }

    public List<Pedidos> buscarPorClienteId(Long clienteId){
        return pedidoRepository.findByClienteId(clienteId);
    }


    public List<Pedidos> buscarPorFechaPedidoRango(Date fechaInicio, Date fechaFin){
        return pedidoRepository.findByFechaPedidoBetween(fechaInicio, fechaFin);
    }


}
