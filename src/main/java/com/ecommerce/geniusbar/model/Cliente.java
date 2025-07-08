package com.ecommerce.geniusbar.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dni_cliente", nullable = false, length = 20, unique = true)
    private Long dni;

    @Column(name = "nombre_cliente", nullable = true, length = 400)
    private String nombre;

    @Column(name = "correo_cliente", nullable = true, length = 800)
    private String correo;

    @Column(name = "telefono_cliente", nullable = true, length = 20)
    private Long telefono;

    @OneToMany(mappedBy = "cliente",// mapeado por el campo cliente en la clase pedidos
    cascade = CascadeType.ALL,//estos son las operaciones que se propagan a la tabla cliente
    orphanRemoval = true,
    fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<Pedidos> pedidos = new ArrayList<>();

    public Cliente () {}

    public Cliente(Long dni, String nombre, String correo, Long telefono){
        this.dni = dni;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.pedidos = new ArrayList<>();
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
    

    public Long getDni(){
        return dni;
    }

    public void setDni(Long dni){
        this.dni = dni;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
            this.nombre = nombre;
    }

    public String getCorreo(){
        return correo;
    }

    public void setCorreo(String correo){
        this.correo = correo;
    }

    public Long getTelefono(){
        return telefono;
    }

    public void setTelefono(Long telefono){
        this.telefono = telefono;
    }

    //Getters y setters para pedidos en Cliente
    public List<Pedidos> getPedidos(){
        return pedidos;
    }

    public void setPedidos(List<Pedidos> pedidos){
        this.pedidos.clear();//Limpia la lista actual
        if(pedidos != null){
            for(Pedidos pedido : pedidos){//Recorre la lista de pedidos
            pedido.setCliente(this);//Establece la referencia inversa
                this.pedidos.add(pedido);//Agrega el pedido a la lista de pedidos
            }
        }
    }





}
