package com.example.cletaeatsbackend.model

import jakarta.persistence.*

@Entity
@Table(name = "pedidos")
data class Pedido(
    @Id
    val id: String,
    val clienteId: String,
    val restauranteId: String,
    val repartidorId: String?,
    @ElementCollection
    val combos: List<PedidoCombo>,
    val precio: Double,
    val distancia: Double,
    val costoTransporte: Double,
    val iva: Double,
    val total: Double,
    val estado: String,
    val horaRealizado: String,
    val horaEntregado: String?,
    val nombreRestaurante: String? = null
)

@Embeddable
data class PedidoCombo(
    val numero: Int,
    val nombre: String,
    val precio: Double
)
