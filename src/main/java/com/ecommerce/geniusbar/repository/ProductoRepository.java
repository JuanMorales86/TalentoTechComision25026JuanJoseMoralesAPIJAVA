package com.ecommerce.geniusbar.repository;

//Importacion de la clase Producto
import com.ecommerce.geniusbar.model.Producto;
//Importacion de la interfaz JpaRepository para el CRUD de productos
import org.springframework.data.jpa.repository.JpaRepository;
//Importacion de la anotacion Repository indicando q es un repositorio
import org.springframework.stereotype.Repository;

// Importaciones necesarias para trabajar con List y BigDecimal
import java.util.List;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

    //Trae automaticamente los metodos findAll(), findById(), save(Producto id), deleteById(Long id), count(), existsById(Long id) estan disponibles.

    //Otros metodos
    // Buscar productos por nombre exacto
    List<Producto> findByNombreProducto(String nombreProducto);

    // Buscar productos q contengan una palabra (Like '%texto%')
    List<Producto> findByNombreProductoContaining(String texto);

    // Buscar productos por precio mayor a un valor
    List<Producto> findByPrecioProductoGreaterThan(BigDecimal precioProducto);

    // Buscar productos por precio entre dos valores
    List<Producto> findByPrecioProductoBetween(BigDecimal min, BigDecimal max);

    // Buscar productos por nombre ignorando mayusculas y minusculas
    List<Producto> findByNombreProductoIgnoreCase(String nombreProducto);

    // Buscar productos ordenados por precio ascendente
    List<Producto> findAllByOrderByPrecioProductoAsc();

    // Buscar productos por nombre y precio mayor a un valor
    List<Producto> findByNombreProductoAndPrecioProductoGreaterThan(String nombreProducto, BigDecimal precioProducto);
    
    // Buscar productos por condicion y categoria
    List<Producto> findByCondicionProducto(String condicionProducto);
    
    // Buscar productos por categoria
    List<Producto> findByCategoriaProducto(String categoriaProducto);
    
    // Buscar productos por id interno
    Optional<Producto> findByIdInterno(String idInterno);
}
