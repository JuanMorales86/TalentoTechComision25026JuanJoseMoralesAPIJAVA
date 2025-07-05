package com.ecommerce.geniusbar.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.geniusbar.model.Pedidos;
import com.ecommerce.geniusbar.service.PedidoService;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/geniusbar/pedidos")
public class PedidoController {

    // Inyeccion de dependencias del servicio Pedido
    public final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @GetMapping//PROBADO
    public ResponseEntity<List<Pedidos>> obtenerPedidos(){
        List<Pedidos> pedidos = pedidoService.todosPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/{id}")//PROBADO
    public ResponseEntity<Pedidos> obtenerPedidosPorId(@PathVariable Long id){
        return pedidoService.encontrarPedido(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{idCliente}")//PROBADO
    public ResponseEntity<Pedidos> crearPedidos(@RequestBody Pedidos pedido, @PathVariable Long idCliente){
        try {
            Pedidos nuevoPedido = pedidoService.crearPedido(pedido, idCliente);
            return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
        } catch (ResponseStatusException e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")//PROBADO
    public ResponseEntity<Pedidos> actualizarPedidos(@PathVariable Long id, @RequestBody Pedidos pedido){
        try {
            Pedidos pedidoActualizado = pedidoService.actualizarPedido(id, pedido);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (ResponseStatusException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")//PROBADO
    public ResponseEntity<Void> eliminarPedidos(@PathVariable Long id){
        if(pedidoService.encontrarPedido(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/busqueda/estado/{estadoPedido}")//PROBADO
    public List<Pedidos> buscarEstadoPedidos(@PathVariable String estadoPedido){
        return pedidoService.buscarPorEstadoPedido(estadoPedido);
    }

    @GetMapping("/busqueda/cliente/{clienteId}")//PROBADO
    public ResponseEntity<List<Pedidos>> buscarPedidosPorClienteId(@PathVariable Long clienteId) {
        List<Pedidos> pedidos = pedidoService.buscarPorClienteId(clienteId);
        if(pedidos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pedidos);// Retorna la lista de pedidos encontrados
    }

    @GetMapping("/busqueda/fecha")//PROBADO
    public ResponseEntity<List<Pedidos>> buscarPorFechaPedido(@RequestParam("fecha") String fechaStr){
        try{
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaInicio = formato.parse(fechaStr);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaInicio);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            Date fechaFin = calendar.getTime();


            List<Pedidos> pedidos = pedidoService.buscarPorFechaPedidoRango(fechaInicio, fechaFin);
            return ResponseEntity.ok(pedidos);
        } catch(java.text.ParseException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de fecha inválido. Usar yyyy-MM-dd", e);
        }
    }



    

}
