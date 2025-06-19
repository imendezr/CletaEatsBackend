package com.example.cletaeatsbackend.controller

import com.example.cletaeatsbackend.model.Cliente
import com.example.cletaeatsbackend.service.ClienteService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clientes")
class ClienteController(private val clienteService: ClienteService) {
    @GetMapping
    fun getClientes(): List<Cliente> = clienteService.getClientes()

    @GetMapping("/active")
    fun getActiveClients(): List<Cliente> = clienteService.getActiveClients()

    @GetMapping("/suspended")
    fun getSuspendedClients(): List<Cliente> = clienteService.getSuspendedClients()

    @PostMapping("/register")
    fun registerCliente(@RequestBody cliente: Cliente): Boolean = clienteService.registerCliente(cliente)

    @PutMapping("/{cedula}")
    fun updateClienteProfile(@PathVariable cedula: String, @RequestBody cliente: Cliente): Boolean =
        clienteService.updateCliente(cedula, cliente)
}
