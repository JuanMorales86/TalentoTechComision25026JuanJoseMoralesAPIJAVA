package com.ecommerce.geniusbar.service;

import com.ecommerce.geniusbar.model.Producto;

import java.util.List;
import java.util.Optional;

//Servicio Producto
public interface ProductoService {
    List<Producto> todosProductos();
    Optional<Producto> encontrarProductos(Long id);
    Producto crearProducto(Producto producto);
    Producto actualizarProducto(Long id , Producto producto);
    void eliminarProducto(Long id);

    List<Producto> buscaProductosPorNombre(String nombre);
    List<Producto> buscarProductosPorTextoEnNombres(String texto);
}
