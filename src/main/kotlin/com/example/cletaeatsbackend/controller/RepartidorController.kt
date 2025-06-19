package com.example.cletaeatsbackend.controller

import com.example.cletaeatsbackend.model.Repartidor
import com.example.cletaeatsbackend.service.RepartidorService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/repartidores")
class RepartidorController(private val repartidorService: RepartidorService) {
    @GetMapping
    fun getRepartidores(): List<Repartidor> = repartidorService.getRepartidores()

    @GetMapping("/no-amonestations")
    fun getRepartidoresSinAmonestaciones(): List<Repartidor> = repartidorService.getRepartidoresSinAmonestaciones()

    @PostMapping("/register")
    fun registerRepartidor(@RequestBody repartidor: Repartidor): Boolean =
        repartidorService.registerRepartidor(repartidor)

    @PutMapping("/{cedula}")
    fun updateRepartidorProfile(@PathVariable cedula: String, @RequestBody repartidor: Repartidor): Boolean =
        repartidorService.updateRepartidor(cedula, repartidor)

    @PostMapping("/{repartidorId}/quejas")
    fun addQueja(@PathVariable repartidorId: String, @RequestBody request: AddQuejaRequest): Boolean =
        repartidorService.addQueja(repartidorId, request.queja, request.addAmonestacion)

    @PostMapping("/reset")
    fun resetRepartidoresEstado(): Boolean = repartidorService.resetRepartidoresEstado()
}

data class AddQuejaRequest(
    val queja: String,
    val addAmonestacion: Boolean
)
