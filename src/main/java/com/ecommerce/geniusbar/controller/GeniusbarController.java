package com.ecommerce.geniusbar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ecommerce.geniusbar.model.Producto;
import com.ecommerce.geniusbar.service.ProductoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/geniusbar/productos")
//ESTE ES MI PRODUCTOCONTROLLER(Articulo)
public class GeniusbarController {

    private final ProductoService productoService;

    @Autowired
    public GeniusbarController(ProductoService productoService){
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> obtenerProducto(){
        return productoService.todosProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerConId(@PathVariable Long id){
        return productoService.encontrarProductos(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Producto crearProductos(@RequestBody Producto producto){
        return productoService.crearProducto(producto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProductos(@PathVariable Long id, @RequestBody Producto producto){
        if(productoService.encontrarProductos(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoService.actualizarProducto(id, producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id){
        if(productoService.encontrarProductos(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/busqueda/nombre/{nombreProducto}")
    public List<Producto> buscaProductosPorNombres(@PathVariable String nombreProducto) {
        return productoService.buscaProductosPorNombre(nombreProducto);
    }

    @GetMapping("/busqueda/nombre/like/{nombreLike}")
    public List<Producto> buscarPorductoPorNombreLike(@PathVariable String nombreLike) {
        return productoService.buscarProductosPorTextoEnNombres(nombreLike);
    }

}
