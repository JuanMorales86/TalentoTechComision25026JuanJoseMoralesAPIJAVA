package com.ecommerce.geniusbar.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Table(name = "pedidos")
public class Pedidos {
    @Id
    @Column(name = "id_pedido")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //Relacion con la tabla cliente
    @ManyToOne(fetch = FetchType.LAZY)//Un pedido pertenece a un cliente
    @JoinColumn(name = "id_cliente", nullable = false)//nombre de la columna en la bd tipo foreing key que referencia a cliente
    @JsonBackReference// Lado "de retorno"
    private Cliente cliente;//Referencia al cliente que hizo el pedido

    @Column(name = "direccion_pedido")
    private String direccionPedido;
    @Column(name = "estado_pedido", length = 50)
    private String estadoPedido;

    @Column(name = "fecha_pedido", updatable = false)
    @Temporal(TemporalType.TIMESTAMP) // como se mapea fecha y la hora al campo de la bd
    private Date fechaPedido;

    //Relacion con la tabla detalle_pedido
    @OneToMany(mappedBy = "pedido", //nombre del campo en detallepedido que mapea esta relacion
    cascade = CascadeType.ALL, //cascade para que se propaguen las operaciones de persistencia
    orphanRemoval = true,//si el detalle se borra de la lista, se borrara en la bd por completo 
    fetch = FetchType.LAZY)
    @JsonManagedReference //Esto lo coloque para que cuando haga un get de pedidos, me traiga los detalles del pedido, es decir, la referencia directa a detallePedido, ya que en detallePedido tengo una referencia inversa a pedidos, entonces con esta referencia me traera los detalles del pedido y no me da el error de recursion infinita, JsonManagedReference por lo que entendi es para que sepa que es la referencia inversa de detallePedido el lado gestor y serealize este lado ya q es el padre.
    private List<DetallePedido> detalles = new ArrayList<>();//Lista de los detalles del pedido
    //referencia inversa Padre
  
    
    //Constructor inicializa 
    public Pedidos(){
        this.detalles = new ArrayList<>();
    }

    public Pedidos(Long id, Cliente cliente, String direccionPedido, String estadoPedido, Date fechaPedido, List<DetallePedido> detalles) {
        this.id = id;
        this.cliente = cliente;
        this.direccionPedido = direccionPedido;
        this.estadoPedido = estadoPedido;
        this.fechaPedido = fechaPedido;
        setDetalles(detalles);
       
    }

    @PrePersist// metodo q se jecuta antes de persistir el objeto en la bd lo hago con el fin de q la fecha no se cambie despues q se cree la primera vez
    public void Oncreate(){
        this.fechaPedido = new Date(); //fecha actual al crear el pedido
    }

    //Getters and Setters
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Cliente getCliente(){
        return cliente;
    }

    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    public Long getIdCliente() {
    return cliente != null ? cliente.getId() : null;
    }

    public String getNombreCliente() {
    return cliente != null ? cliente.getNombre() : null;
    }
  

    public String getDireccionPedido(){
        return direccionPedido;
    }

    public void setDireccionPedido(String direccionPedido){
        this.direccionPedido = direccionPedido;
    }

    public String getEstadoPedido(){
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido){
        this.estadoPedido = estadoPedido;
    }

    public Date getFechaPedido(){
        return fechaPedido;
    }


    //getters y setters para detalles
    public List<DetallePedido> getDetalles(){
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles){
        this.detalles.clear(); // Limpia la lista actual
        if(detalles != null){
            for(DetallePedido detalle : detalles){
                detalle.setPedido(this); // Establece la referencia inversa
                this.detalles.add(detalle);// Agrega el detalle a la lista
            }
        }
    }

    public void agregarDetalle(DetallePedido detalle){
        detalle.setPedido(this); //Referencia al padre
        this.detalles.add(detalle);
    }

}
