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
    
    
}
