package com.ina.sysVentas.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Data;


@Data
@Entity
public class DetalleVenta implements Serializable{
    
    private static final long serialVersionUID=1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_detalle")
    private long idDetalle;
    
    @NotNull(message = "La Cantidad es Obligatoria")
    @Min(value = 1, message = "Debe indicar una Cantidad mayor a 0")
    private int cantidad;
    
    @NotNull(message = "Debe indicar el Precio de Venta")
    @Column(name="precio_venta")
    private double precio;
    
    @JoinColumn(name="id_producto", nullable = false)
    @ManyToOne(optional = false)
    private Producto producto;
    
    @JoinColumn(name="id_venta", nullable = false)
    @ManyToOne(optional = false)
    private Venta venta; 
    
    
}
