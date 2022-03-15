package com.ina.sysVentas.controller;

import com.ina.sysVentas.domain.Producto;
import com.ina.sysVentas.services.IProductoService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductosController {
    
    @Autowired
    private IProductoService productoService;
    
    @GetMapping("/productos")
    public String listar(Model model){
        List<Producto> list = productoService.listar();
        model.addAttribute("productos", list);
        return "listaProductos";
    }
    
    @PostMapping("/guardarProducto")
    public String guardar(@Valid Producto producto, Errors errors){
        if(errors.hasErrors())
            return "producto";
        
        productoService.guardar(producto);
        return "redirect:/productos";
    }
    
    @GetMapping("/eliminarProducto/{idProducto}")
    public String eliminar(Producto producto, RedirectAttributes redirAtt){
        productoService.eliminar(producto);
        String msg="Se ha eliminado al producto";
        redirAtt.addFlashAttribute("msg", msg);
        return "redirect:/productos";
    }
    
    @GetMapping("/nuevoProducto")
    public String nuevo(Producto producto){
        return "producto";
    }
    
    @GetMapping("/editarProducto/{idProducto}")
    public String editar(Producto producto, Model model, RedirectAttributes redirAtt){
        producto = productoService.obtenUno(producto.getIdProducto());
        if(producto != null){
            model.addAttribute("producto", producto);
            return "producto"; 
        }else{
            String msg="Imposible cargar el Producto";
            redirAtt.addFlashAttribute("msg", msg);
            return "redirect:/productos";
        }
        
    }
    
    @PostMapping("/buscarProducto")
    public String buscar(String descripcion, Model model){
        
        if(!descripcion.isEmpty()){
            String msg="Lista Filtrada ['"+ descripcion +"']";
            model.addAttribute("msg", msg);
        }
        
        List<Producto> lista = productoService.listar(descripcion);
        model.addAttribute("productos",lista);
        return "listaProductos";
    }
    
    
}
