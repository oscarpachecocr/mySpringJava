package com.ina.sysVentas.services;

import com.ina.sysVentas.domain.DetalleVenta;
import com.ina.sysVentas.domain.Factura;
import com.ina.sysVentas.domain.Venta;
import java.util.Calendar;
import java.util.List;

public interface IVentasService {
    public List<Venta> listarVentas();
    public List<Venta> listarVentas(boolean cancelada);
    public List<Venta> listarVentas(Calendar fecha);
    public List<DetalleVenta> listarDetalles(long idVenta);
    
    public Venta bucarVenta(long id);
    public DetalleVenta bucarDetalle(long id);
    
    public Factura guardar(Factura factura);
    
    public void eliminar(Venta venta);
    
    public Factura eliminarDetale(DetalleVenta detalle);
    
    public int pagarVenta(long idVenta);
}
