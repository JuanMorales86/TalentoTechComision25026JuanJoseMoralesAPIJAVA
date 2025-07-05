package com.ecommerce.geniusbar.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ecommerce.geniusbar.model.Pedidos;

//Class servicio Pedido
public interface PedidoService {
    List<Pedidos> todosPedidos();
    Optional<Pedidos> encontrarPedido(Long id);
    Pedidos crearPedido(Pedidos pedido, Long idCliente);
    Pedidos actualizarPedido(Long id, Pedidos pedido);
    void eliminarPedido(Long id);

    List<Pedidos> buscarPorEstadoPedido(String estadoPedido);

    List<Pedidos> buscarPorClienteId(Long clienteId);

    List<Pedidos> buscarPorFechaPedidoRango(Date fechaInicio, Date fechaFin);
}
