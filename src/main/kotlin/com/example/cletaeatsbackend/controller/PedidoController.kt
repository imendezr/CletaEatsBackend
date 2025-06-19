package com.example.cletaeatsbackend.controller

import com.example.cletaeatsbackend.model.Cliente
import com.example.cletaeatsbackend.model.Pedido
import com.example.cletaeatsbackend.model.Restaurante
import com.example.cletaeatsbackend.service.CreateOrderRequest
import com.example.cletaeatsbackend.service.PedidoService
import com.example.cletaeatsbackend.service.UpdateStatusRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pedidos")
class PedidoController(private val pedidoService: PedidoService) {
    @GetMapping
    fun getPedidos(): List<Pedido> = pedidoService.getPedidos()

    @PostMapping
    fun createOrder(@RequestBody request: CreateOrderRequest): Pedido? = pedidoService.createOrder(request)

    @PostMapping("/{orderId}/status")
    fun updateOrderStatus(@PathVariable orderId: String, @RequestBody request: UpdateStatusRequest): Boolean =
        pedidoService.updateOrderStatus(orderId, request)

    @GetMapping("/reports/revenue/restaurant")
    fun getTotalRevenueByRestaurant(): Map<String, Double> = pedidoService.getTotalRevenueByRestaurant()

    @GetMapping("/reports/restaurant/most-orders")
    fun getRestauranteConMasPedidos(): Restaurante? = pedidoService.getRestauranteConMasPedidos()

    @GetMapping("/reports/restaurant/least-orders")
    fun getRestauranteConMenosPedidos(): Restaurante? = pedidoService.getRestauranteConMenosPedidos()

    @GetMapping("/reports/repartidor/quejas")
    fun getQuejasPorRepartidor(): Map<String, List<String>> = pedidoService.getQuejasPorRepartidor()

    @GetMapping("/reports/cliente/pedidos")
    fun getPedidosPorCliente(): Map<String, List<Pedido>> = pedidoService.getPedidosPorCliente()

    @GetMapping("/reports/cliente/most-orders")
    fun getClienteConMasPedidos(): Cliente? = pedidoService.getClienteConMasPedidos()

    @GetMapping("/reports/pedidos/hora-pico")
    fun getHoraPicoPedidos(): String? = pedidoService.getHoraPicoPedidos()
}
