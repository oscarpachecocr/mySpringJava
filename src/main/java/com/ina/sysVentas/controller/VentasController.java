package com.ina.sysVentas.controller;

import com.ina.sysVentas.domain.Cliente;
import com.ina.sysVentas.domain.DetalleVenta;
import com.ina.sysVentas.domain.Factura;
import com.ina.sysVentas.domain.Producto;
import com.ina.sysVentas.domain.Venta;
import com.ina.sysVentas.services.IClienteService;
import com.ina.sysVentas.services.IVentasService;
import com.ina.sysVentas.services.IProductoService;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VentasController {

    @Autowired
    private IVentasService ventaService;

    @Autowired
    private IProductoService productoService;
    
    @Autowired
    private IClienteService clienteService;

    @PostMapping("/guardarFactura")
    public String facturar(@Valid Factura factura, Errors er, Model model) {
        List<DetalleVenta> detalles;
        List<Producto> productos = productoService.listar();
        double total = 0.0;

        if (er.hasErrors()) {
            detalles = ventaService.listarDetalles(factura.getIdVenta());
            factura.setDetalles(detalles);
            total = detalles.stream()
                    .map(item -> item.getCantidad() * item.getPrecio())
                    .reduce((subtotal, valor) -> subtotal + valor)
                    .orElse(0.0);

            model.addAttribute("productos", productos);
            model.addAttribute("factura", factura);
            model.addAttribute("total", total);
            return "facturar";
        }

        factura = ventaService.guardar(factura);
        factura.setCantidad(0);
        factura.setIdProducto(0);
        factura.setDescripcion("");

        detalles = ventaService.listarDetalles(factura.getIdVenta());
        factura.setDetalles(detalles);
        total = detalles.stream()
                .map(item -> item.getCantidad() * item.getPrecio())
                .reduce((subtotal, valor) -> subtotal + valor)
                .orElse(0.0);

        String msg = "";
        switch (factura.getRetorno()) {
            case 1:
                msg = "La factura fue pagada, no se puede actualizar";
                break;
            case 2:
                msg = "El stock es insuficiente";
                break;
            case 3://Éxito
                msg = null;
                break;
            default:
                msg = "Error Inesperado";
        }

        model.addAttribute("productos", productos);
        model.addAttribute("factura", factura);
        model.addAttribute("total", total);
        model.addAttribute("msg", msg);

        return "facturar";
    }
    
    @GetMapping("/facturar/{idCliente}")
    public String abrirFactura (Cliente cliente, Model model){
        Factura factura = new Factura();
        cliente = clienteService.obtenerCliente(cliente.getIdCliente());
        factura.setIdCliente(cliente.getIdCliente());
        factura.setNombreCliente(cliente.getNombre() + " " + cliente.getApellido());
        
        List<Producto> lista  = productoService.listar();
        
        model.addAttribute("productos", lista);
        model.addAttribute("factura", factura);
        return "facturar";
    }
    
    @GetMapping("/facturas")
    public String listar (Model model){    
        
        List<Venta> lista = ventaService.listarVentas(false);
        
        model.addAttribute("ventas", lista);
        return "listaVentas";
    }
    
    @GetMapping("/pagar/{idVenta}")
    public String cancelar(Venta venta){
        venta=ventaService.bucarVenta(venta.getIdVenta());
        ventaService.pagarVenta(venta.getIdVenta());
        return "redirect:/facturas";
    }
    
    @GetMapping("/eliminarDetalle")
    public String eliminarDetalle(DetalleVenta detalle, Model model){
        double total=0;
        Factura factura=ventaService.eliminarDetale(detalle);
        List<Producto> productos = productoService.listar();
        total = factura.getDetalles().stream()
                .map(item -> item.getCantidad() * item.getPrecio())
                .reduce((subtotal, valor) -> subtotal + valor)
                .orElse(0.0);
        
        model.addAttribute("total", total);
        model.addAttribute("productos", productos);
        model.addAttribute("factura", factura);
        return "facturar";
    }
    
    @GetMapping("/editarVenta/{idVenta}")
    public String editar(Venta venta, Model model) {
        double total = 0;
        venta = ventaService.bucarVenta(venta.getIdVenta());

        Factura factura = new Factura();
        factura.setIdVenta(venta.getIdVenta());
        factura.setFecha(venta.getFecha());
        factura.setIdCliente(venta.getCliente().getIdCliente());
        factura.setNombreCliente(venta.getCliente().getNombre() + " " + venta.getCliente().getApellido());
        factura.setTipo(venta.getTipo());
        factura.setDetalles(venta.getDetalles());

        List<Producto> productos = productoService.listar();
        total = factura.getDetalles().stream().map(item -> item.getCantidad() * item.getPrecio()).reduce((subtotal, valor) -> subtotal + valor).orElse(0.0);
        model.addAttribute("total", total);
        model.addAttribute("msg", null);
        model.addAttribute("productos", productos);
        model.addAttribute("factura", factura);

        return "facturar";
    }

    @GetMapping("/eliminarVenta/{idVenta}")
    public String eliminarVenta(Venta venta) {

        ventaService.eliminar(venta);
        
        return "redirect:/facturas";
    }
}
