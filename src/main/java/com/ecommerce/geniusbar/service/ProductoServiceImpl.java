package com.ecommerce.geniusbar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.geniusbar.model.Producto;
import com.ecommerce.geniusbar.repository.ProductoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService  {

    //Inyeccion de dependencias del repositorio
    private final ProductoRepository productoRepository;

    //Constructor para inyectar el repositorio
    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    public List<Producto> todosProductos(){
        return productoRepository.findAll();
    }

    public Optional<Producto> encontrarProductos(Long id){
        return productoRepository.findById(id);
    }

    public Producto crearProducto(Producto producto){
        if(producto.getIdInterno() == null || producto.getIdInterno().trim().isEmpty()){
            producto.setIdInterno(generarIdInterno());
        }
        return productoRepository.save(producto);
    }
    private String generarIdInterno(){
        int counter = 1;
        String newIdInterno;
        boolean idExists;

        do {
            newIdInterno = "GEN_" + String.format("%05d", counter);//formateo como lo quiero, 5 digitos con ceros a la izquierda
            idExists = productoRepository.findByIdInterno(newIdInterno).isPresent();//verifico si id ya existe en la bd
            if(idExists) {
                counter++;//si existe incrementa el counter en 1
            }
            } while (idExists); // si no repite hasta q no exista el id

            return newIdInterno;
    }

    public Producto actualizarProducto(Long id, Producto producto){
        producto.setId(id);
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id){
        productoRepository.deleteById(id);
    }

    public List<Producto> buscaProductosPorNombre(String nombre){
        return productoRepository.findByNombreProducto(nombre);
    }

    public List<Producto> buscarProductosPorTextoEnNombres(String texto){
        return productoRepository.findByNombreProductoContaining(texto);
    }

    public List<Producto> buscarProductosPorCondicion(String condicionProducto){
        return productoRepository.findByCondicionProducto(condicionProducto);
    }

    public List<Producto> buscarProductosPorCategoria(String categoriaProducto){
        return productoRepository.findByCategoriaProducto(categoriaProducto);
    }

    public List<Producto> buscarProductosPorColor(String colorProducto){
        return productoRepository.findByColorProducto(colorProducto);
    }

    public Optional<Producto> buscarProductoPoridInterno(String idInterno){
        return productoRepository.findByIdInterno(idInterno);
    }

    public List<Producto> buscarProductosPorPrecioMayor(BigDecimal precioProducto){
        return productoRepository.findByPrecioProductoGreaterThan(precioProducto);
    }

}
