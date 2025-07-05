package com.ecommerce.geniusbar.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detallepedido")
//Contrato de DetallePedido
public class DetallePedido {
    @Id//marca la propiedad clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)//generacion autoincremento automatica del id
    @Column(name = "id_detalle_pedido")//nombre de la columna en la bd
    private Long id;
    
    @ManyToOne//Un detallePedido pertenece a un producto (osea un detalle tiene muchos productos)
    @JoinColumn(name = "id_producto", nullable = false)//nombre de la columna en la bd tipo foreing key que referencia a producto 
   
    private Producto producto;//El articulo en cuestion

    @Column(name = "cantidad_producto", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;// para llevar el historial de cuando se creo el pedido y el historial del precio del producto

    @ManyToOne // Un detallePedido pertenece a un pedido
    @JoinColumn(name = "id_pedido", nullable = false) // nombre de la columna en la bd tipo foreing key que referencia a pedido
    @JsonBackReference //Esto se lo coloque por que si bien no me daba un error cuando hacia un get de pedidos me traia como repetido los detalles y el pedido y me salia un error q excedia el json, por lo que lei esta referencia JsonBackReference funciona cuando hay datos bidireccionales, esta le dice entonces al Jackson que no serealice esta referencia por q es el retorno, ya que el padre en pedidos.java ya hace la serealizacion , esta solucion la encontre para resolver el problema de la llamada recursion infinita.
    private Pedidos pedido;//pedido padre al que pertenece este detalle

    //Constructor vacio
    public DetallePedido(){}

    //Constructor inicializador de DetallePedido
    public DetallePedido(Producto producto, Integer cantidad, BigDecimal precioUnitario, Pedidos pedido){
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.pedido = pedido;
    }

    //Getters and Setters
    public Producto getProducto(){
        return producto;
    }

    public void setProducto(Producto producto){
        this.producto = producto;
    }

    public Integer getCantidad(){
        return cantidad;
    }

    public void setCantidad(Integer cantidad){
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario(){
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario){
        this.precioUnitario = precioUnitario;
    }

    public Pedidos getPedido(){
        return pedido;
    }

    public void setPedido(Pedidos pedido){
        this.pedido = pedido;
    }

}
