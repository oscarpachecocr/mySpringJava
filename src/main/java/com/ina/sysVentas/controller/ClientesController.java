
package com.ina.sysVentas.controller;

import com.ina.sysVentas.domain.Cliente;
import com.ina.sysVentas.services.ClienteService;
import com.ina.sysVentas.services.IClienteService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ClientesController {

    @Autowired
    private IClienteService servicioCliente;
    
    @GetMapping("/")
    public String inicio(){
        return "index";
    }
    
    @GetMapping("/clientes")
    public String listaClientes(Model model){
        List<Cliente> lista = servicioCliente.listar();
        model.addAttribute("clientes", lista);
        return "listaClientes";
    }
}
