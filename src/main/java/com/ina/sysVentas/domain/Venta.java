package com.ina.sysVentas.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
       name = "facturar",
       procedureName = "FACTURAR",
       parameters = {
           @StoredProcedureParameter(mode = ParameterMode.IN, name="TIPO",type = String.class),
           @StoredProcedureParameter(mode = ParameterMode.IN, name="ID_CLIENTE",type = Long.class),
           @StoredProcedureParameter(mode = ParameterMode.IN, name="ID_PRODUCTO",type = Long.class),
           @StoredProcedureParameter(mode = ParameterMode.IN, name="CANTIDAD",type = Integer.class),
           @StoredProcedureParameter(mode = ParameterMode.IN, name="PRECIO_VENTA",type = Double.class),
           @StoredProcedureParameter(mode = ParameterMode.OUT, name="ID_VENTA",type = Long.class),
           @StoredProcedureParameter(mode = ParameterMode.OUT, name="retorno",type = Integer.class)
       }
    )
})
public class Venta implements Serializable{
    
    private static final long serialVersionUID=1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_venta")
    private long idVenta;
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Calendar fecha;
    
    private String tipo;
    
    private boolean cancelada;
    
    @JoinColumn(name="id_cliente",nullable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;
    
    @OneToMany(mappedBy = "venta")
    private List<DetalleVenta> detalles;
}
