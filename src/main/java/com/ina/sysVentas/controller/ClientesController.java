
package com.ina.sysVentas.controller;

import com.ina.sysVentas.domain.Cliente;
import com.ina.sysVentas.services.IClienteService;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ClientesController {

    @Autowired
    private IClienteService servicioCliente;
    
    @GetMapping("/clientes")
    public String listar(Model model){
        List<Cliente> lista = servicioCliente.listar();
        model.addAttribute("clientes", lista);
        return "listaClientes";
    }
   
    @PostMapping("/filtrarClientes")
    public String filtrar(String txtTexto, Model model){
        List<Cliente> lista = servicioCliente.listar(txtTexto, txtTexto);
        model.addAttribute("clientes", lista);
        return "listaClientes";
    }
    
    @GetMapping("/nuevoCliente")
    public String nuevo(Cliente cliente){
        return "cliente";
    }
    
    @PostMapping("/guardarCliente")
    public String guardar(@Valid Cliente cliente, Errors er){
        
        if(er.hasErrors()){
            return "cliente";
        }
        
        servicioCliente.guardar(cliente);
        return "redirect:/clientes";
    }
    
    @GetMapping("/editarCliente/{idCliente}")
    public String editar(Cliente cliente, Model model){
        
        cliente = servicioCliente.obtenerCliente(cliente.getIdCliente());
        if(cliente != null){
            model.addAttribute("cliente", cliente);
            return "cliente";
        }
        String msg="No se logró cargar el cliente";
        List<Cliente> lista = servicioCliente.listar();
        model.addAttribute("clientes", lista);
        model.addAttribute("msg", msg);
        return "listaClientes";
    }
    
    @GetMapping("/eliminarCliente")
    public String eliminar(Cliente cliente, Model model){
        int resultado = servicioCliente.eliminar(cliente);
        String msg="";
        if(resultado == 0){
            msg="No se puede eliminar porque tiene ventas asocaidas";
        }else{
            msg="Cliente Eliminado!";
        }
        
        //Esto no se gace
        List<Cliente> lista = servicioCliente.listar();
        model.addAttribute("clientes", lista);
        //Hasta Aquí
        
        model.addAttribute("msg", msg);
        return "listaClientes";
    }
}
