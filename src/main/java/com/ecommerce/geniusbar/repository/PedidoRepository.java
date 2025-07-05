package com.ecommerce.geniusbar.repository;

import java.util.Date;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.geniusbar.model.Pedidos;

@Repository
public interface PedidoRepository extends JpaRepository<Pedidos, Long> {

    //Metodos automaticos > JpaRepository los provee findAll, findById, save, deleteById

    //Otros Metodos
    List<Pedidos> findByEstadoPedido(String estadoPedido);
    List<Pedidos> findByClienteId(Long clienteId);
    List<Pedidos> findByFechaPedidoBetween(Date fechaInicio, Date fechaFin);
}
