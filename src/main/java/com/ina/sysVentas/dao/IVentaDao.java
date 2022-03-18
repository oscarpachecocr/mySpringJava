package com.ina.sysVentas.dao;

import com.ina.sysVentas.domain.Venta;
import java.util.Calendar;
import java.util.HashMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IVentaDao extends JpaRepository<Venta,Long>{
    public Iterable<Venta> findByCancelada(boolean cancelada);
    public Iterable<Venta> findByFecha(Calendar fecha);
    
    
    @Procedure(name="facturar")
    public HashMap facturar(
            @Param("TIPO") String tipo,
            @Param("ID_CLIENTE") long idCliente,
            @Param("ID_PRODUCTO") long idProducto,
            @Param("CANTIDAD") int cantidad,
            @Param("PRECIO_VENTA") double precio,
            @Param("ID_VENTA") long idVenta,
            @Param("retorno") int retorno);
    
    
    @Procedure(procedureName = "CANCELAR_FACTURA",outputParameterName = "res")
    public Integer cancelarFactura(@Param("ID") long id);
}
