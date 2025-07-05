package com.ecommerce.geniusbar.model;

import java.math.BigDecimal;

import jakarta.persistence.*;


@Entity
@Table(name = "producto")
public class Producto {

    @Id//se marca esta propiedad como clave primaria
    @Column(name = "id_producto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre_producto")
    private String nombreProducto;
    @Column(name = "descripcion_producto", length = 1000)
    private String descripcionProducto;
    @Column(name = "precio_producto", precision = 10, scale = 2, nullable = false) // Precision y escala para BigDecimal
    private BigDecimal precioProducto;
    @Column(name = "condicion_producto")
    private String condicionProducto;
    @Column(name = "categoria_producto")
    private String categoriaProducto;
    @Column(name = "id_interno", unique = true)
    private String idInterno;//Formato GEN_XXXXX
    @Column(name = "color_producto")
    private String colorProducto;
    @Column(name = "observaciones_producto", length = 1000)
    private String observacionesProducto;



    public Producto(){}

    public Producto(Long id, String nombreProducto, String descripcionProducto, BigDecimal precioProducto, String condicionProducto,String categoriaProducto, String idInterno, String colorProducto, String observacionesProducto) {
        this.id = id;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioProducto = precioProducto;
        this.condicionProducto = condicionProducto;
        this.categoriaProducto = categoriaProducto;
        this.idInterno = idInterno;
        this.colorProducto = colorProducto;
        this.observacionesProducto = observacionesProducto;
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNombreProducto(){
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto){
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto(){
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto){
        this.descripcionProducto = descripcionProducto;
    }

    public BigDecimal getPrecioProducto() {
        return precioProducto;
    }
    public void setPrecioProducto(BigDecimal precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getCondicionProducto() {
        return condicionProducto;
    }

    public void setCondicionProducto(String condicionProducto) {
        this.condicionProducto = condicionProducto;
    }

    
    public String getCategoriaProducto() {
        return categoriaProducto;
    }
    
    public void setCategoriaProducto(String categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }
    
    public String getIdInterno() {
        return idInterno;
    }

    public void setIdInterno(String idInterno) {
        this.idInterno = idInterno;
    }

     public String getColorProducto() {
        return colorProducto;
    }

    public void setColorProducto(String colorProducto) {
        this.colorProducto = colorProducto;
    }

    public String getObservacionesProducto() {
        return observacionesProducto;
    }

    public void setObservacionesProducto(String observacionesProducto) {
        this.observacionesProducto = observacionesProducto;
    }

}
