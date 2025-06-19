package com.example.cletaeatsbackend.service

import com.example.cletaeatsbackend.model.Cliente
import com.example.cletaeatsbackend.repository.ClienteRepository
import org.springframework.stereotype.Service

@Service
class ClienteService(private val clienteRepository: ClienteRepository) {
    fun getClientes(): List<Cliente> = clienteRepository.findAll()
    fun getActiveClients(): List<Cliente> = clienteRepository.findByEstado("activo")
    fun getSuspendedClients(): List<Cliente> = clienteRepository.findByEstado("suspendido")
    fun registerCliente(cliente: Cliente): Boolean {
        if (clienteRepository.findByCedula(cliente.cedula) != null) return false
        clienteRepository.save(cliente)
        return true
    }

    fun updateCliente(cedula: String, cliente: Cliente): Boolean {
        val existing = clienteRepository.findByCedula(cedula) ?: return false
        clienteRepository.save(cliente.copy(id = existing.id))
        return true
    }
}
