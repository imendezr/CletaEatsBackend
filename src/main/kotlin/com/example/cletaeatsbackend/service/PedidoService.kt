package com.example.cletaeatsbackend.service

import com.example.cletaeatsbackend.model.Cliente
import com.example.cletaeatsbackend.model.Pedido
import com.example.cletaeatsbackend.model.PedidoCombo
import com.example.cletaeatsbackend.model.Restaurante
import com.example.cletaeatsbackend.repository.ClienteRepository
import com.example.cletaeatsbackend.repository.PedidoRepository
import com.example.cletaeatsbackend.repository.RepartidorRepository
import com.example.cletaeatsbackend.repository.RestauranteRepository
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class PedidoService(
    private val pedidoRepository: PedidoRepository,
    private val clienteRepository: ClienteRepository,
    private val restauranteRepository: RestauranteRepository,
    private val repartidorRepository: RepartidorRepository
) {
    fun getPedidos(): List<Pedido> = pedidoRepository.findAll()

    fun createOrder(request: CreateOrderRequest): Pedido? {
        val cliente = clienteRepository.findById(request.clienteId).orElse(null) ?: return null
        if (cliente.estado != "activo") return null
        val restaurante = restauranteRepository.findById(request.restauranteId).orElse(null) ?: return null
        request.combos.forEach { combo ->
            if (restaurante.combos.none { it.numero == combo.numero && it.nombre == combo.nombre && it.precio == combo.precio }) return null
        }
        val availableRepartidores = repartidorRepository.findByEstado("disponible").filter { it.amonestaciones < 4 }
        val repartidor = availableRepartidores.shuffled().firstOrNull() ?: return null
        val isFeriado = false
        val costoPorKm = if (isFeriado) repartidor.costoPorKmFeriados else repartidor.costoPorKmHabiles
        val precio = request.combos.sumOf { it.precio }
        val costoTransporte = request.distancia * costoPorKm
        val iva = precio * 0.13
        val total = precio + costoTransporte + iva
        val pedido = Pedido(
            id = UUID.randomUUID().toString(),
            clienteId = request.clienteId,
            restauranteId = request.restauranteId,
            repartidorId = repartidor.id,
            combos = request.combos,
            precio = precio,
            distancia = request.distancia,
            costoTransporte = costoTransporte,
            iva = iva,
            total = total,
            estado = "en preparación",
            horaRealizado = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()),
            horaEntregado = null,
            nombreRestaurante = restaurante.nombre
        )
        repartidorRepository.save(repartidor.copy(estado = "ocupado"))
        pedidoRepository.save(pedido)
        return pedido
    }

    fun updateOrderStatus(orderId: String, request: UpdateStatusRequest): Boolean {
        val pedido = pedidoRepository.findById(orderId).orElse(null) ?: return false
        if (request.newStatus !in listOf("en preparación", "en camino", "entregado", "suspendido")) return false
        when (request.userType) {
            "RestauranteUser" -> {
                if (request.userId != pedido.restauranteId) return false
                when (pedido.estado) {
                    "en preparación" -> if (request.newStatus !in listOf("en camino", "suspendido")) return false
                    "suspendido" -> if (request.newStatus !in listOf("en preparación", "en camino")) return false
                    else -> return false
                }
            }

            "RepartidorUser" -> {
                if (request.userId != pedido.repartidorId) return false
                if (pedido.estado != "en camino" || request.newStatus != "entregado") return false
            }

            else -> return false
        }
        val updatedPedido = if (request.newStatus == "entregado") {
            val repartidor = repartidorRepository.findById(pedido.repartidorId ?: "").orElse(null) ?: return false
            repartidorRepository.save(
                repartidor.copy(
                    kmRecorridosDiarios = repartidor.kmRecorridosDiarios + pedido.distancia,
                    estado = "disponible"
                )
            )
            pedido.copy(
                estado = request.newStatus,
                horaEntregado = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            )
        } else pedido.copy(estado = request.newStatus)
        pedidoRepository.save(updatedPedido)
        return true
    }

    // Reportes
    fun getTotalRevenueByRestaurant(): Map<String, Double> =
        pedidoRepository.findAll().groupBy { it.restauranteId }.mapValues { it.value.sumOf { it.total } }

    fun getRestauranteConMasPedidos(): Restaurante? {
        val pedidosPorRestaurante = pedidoRepository.findAll().groupBy { it.restauranteId }
        val maxPedidos = pedidosPorRestaurante.maxByOrNull { it.value.size }?.key ?: return null
        return restauranteRepository.findById(maxPedidos).orElse(null)
    }

    fun getRestauranteConMenosPedidos(): Restaurante? {
        val pedidosPorRestaurante = pedidoRepository.findAll().groupBy { it.restauranteId }
        val minPedidos = pedidosPorRestaurante.minByOrNull { it.value.size }?.key ?: return null
        return restauranteRepository.findById(minPedidos).orElse(null)
    }

    fun getQuejasPorRepartidor(): Map<String, List<String>> =
        repartidorRepository.findAll().associate { it.id to it.quejas }

    fun getPedidosPorCliente(): Map<String, List<Pedido>> =
        pedidoRepository.findAll().groupBy { it.clienteId }

    fun getClienteConMasPedidos(): Cliente? {
        val pedidosPorCliente = pedidoRepository.findAll().groupBy { it.clienteId }
        val maxPedidos = pedidosPorCliente.maxByOrNull { it.value.size }?.key ?: return null
        return clienteRepository.findById(maxPedidos).orElse(null)
    }

    fun getHoraPicoPedidos(): String? {
        val pedidos = pedidoRepository.findAll()
        if (pedidos.isEmpty()) return null
        val horas = pedidos.groupBy { it.horaRealizado.substring(11, 13) }
        return horas.maxByOrNull { it.value.size }?.key
    }
}

data class CreateOrderRequest(
    val clienteId: String,
    val restauranteId: String,
    val combos: List<PedidoCombo>,
    val distancia: Double
)

data class UpdateStatusRequest(
    val newStatus: String,
    val userType: String,
    val userId: String
)
