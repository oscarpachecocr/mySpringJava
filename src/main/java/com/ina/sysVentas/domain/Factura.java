package com.ina.sysVentas.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Factura implements Serializable{
    private static final long serialVersionUID=1L;
    
    private long idVenta;
    
    private String tipo;
    private Calendar fecha;
    
    private long idCliente;
    @NotEmpty(message = "El Cliente es Obligatorio")
    private String nombreCliente;
    
    private long idProducto;
    @NotEmpty(message = "El Producto es Obligatorio")
    private String descripcion;
    
    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad debe ser superior a cero")
    private int cantidad;
    
    private double precio;
    
    private int retorno;
    
    private List<DetalleVenta> detalles;
}
