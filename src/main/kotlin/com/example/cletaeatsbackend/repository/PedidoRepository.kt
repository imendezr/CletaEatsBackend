package com.example.cletaeatsbackend.repository

import com.example.cletaeatsbackend.model.Pedido
import org.springframework.data.jpa.repository.JpaRepository

interface PedidoRepository : JpaRepository<Pedido, String> {
    fun findByClienteId(clienteId: String): List<Pedido>
    fun findByRestauranteId(restauranteId: String): List<Pedido>
    fun findByEstado(estado: String): List<Pedido>
}
