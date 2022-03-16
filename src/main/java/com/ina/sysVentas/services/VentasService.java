
package com.ina.sysVentas.services;

import com.ina.sysVentas.dao.IDetalleVentaDao;
import com.ina.sysVentas.dao.IVentaDao;
import com.ina.sysVentas.dao.IProductoDao;
import com.ina.sysVentas.domain.DetalleVenta;
import com.ina.sysVentas.domain.Factura;
import com.ina.sysVentas.domain.Venta;
import com.ina.sysVentas.domain.Producto;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentasService implements IVentasService{

    @Autowired
    private IVentaDao ventaDao;
    
    @Autowired
    private IDetalleVentaDao detalleDao;
    
    @Autowired
    private IProductoDao productoDao;
    
    @Override
    public List<Venta> listarVentas() {
        return ventaDao.findAll();
    }

    @Override
    public List<Venta> listarVentas(boolean cancelada) {
        return (List<Venta>) ventaDao.findByCancelada(cancelada);
    }

    @Override
    public List<Venta> listarVentas(Calendar fecha) {
        return (List<Venta>) ventaDao.findByFecha(fecha);
    }

    @Override
    public List<DetalleVenta> listarDetalles(long idVenta) {
        Venta tempVenta = new Venta();
        tempVenta.setIdVenta(idVenta);
        return (List<DetalleVenta>) detalleDao.findByVenta(tempVenta);
    }

    @Override
    public Venta bucarVenta(long id) {
        return ventaDao.findById(id).orElse(null);
    }

    @Override
    public DetalleVenta bucarDetalle(long id) {
        return detalleDao.findById(id).orElse(null);
    }

    @Override
    public Factura guardar(Factura factura) {
        Producto prod = productoDao.findById(factura.getIdProducto()).orElse(null);
        if(prod != null){
            factura.setPrecio(prod.getPrecio());
        }
        
        HashMap resultado = ventaDao.facturar(
                factura.getTipo(),
                factura.getIdCliente(),
                factura.getIdProducto(),
                factura.getCantidad(),
                factura.getPrecio(), 
                factura.getIdVenta(),
                factura.getRetorno());
        factura.setIdVenta((long)resultado.get("idVenta"));
        factura.setRetorno((int)resultado.get("retorno"));
        return factura;
    }

    @Override
    public void eliminar(Venta venta) {
        venta = ventaDao.findById(venta.getIdVenta()).orElse(null);
        if(venta!=null){
            if(!venta.isCancelada()){
                ventaDao.delete(venta);
            }
        }
    }

    @Override
    public Factura eliminarDetale(DetalleVenta detalle) {
        detalle = detalleDao.findById(detalle.getIdDetalle()).orElse(null);
        Factura factura = new Factura();
        if(detalle != null){
            Venta venta = ventaDao.findById(detalle.getVenta().getIdVenta()).orElse(null);
            if(venta != null){
                factura.setIdVenta(venta.getIdVenta());
                factura.setIdCliente(venta.getCliente().getIdCliente());
                factura.setNombreCliente(venta.getCliente().getNombre() +
                        " " +
                        venta.getCliente().getApellido());
                factura.setTipo(venta.getTipo());
                factura.setFecha(venta.getFecha());
                factura.setDetalles(venta.getDetalles());
            }
            if(!venta.isCancelada()){
                detalleDao.delete(detalle);
                factura.setDetalles(listarDetalles(venta.getIdVenta()));
            }
        }
        return factura;
    }

    @Override
    public int pagarVenta(long idVenta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
