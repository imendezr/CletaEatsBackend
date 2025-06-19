package com.example.cletaeatsbackend.controller

import com.example.cletaeatsbackend.model.Restaurante
import com.example.cletaeatsbackend.service.RestauranteService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/restaurantes")
class RestauranteController(private val restauranteService: RestauranteService) {
    @GetMapping
    fun getRestaurantes(): List<Restaurante> = restauranteService.getRestaurantes()

    @PostMapping("/register")
    fun registerRestaurante(@RequestBody restaurante: Restaurante): Boolean =
        restauranteService.registerRestaurante(restaurante)

    @PutMapping("/{cedulaJuridica}")
    fun updateRestauranteProfile(@PathVariable cedulaJuridica: String, @RequestBody restaurante: Restaurante): Boolean =
        restauranteService.updateRestaurante(cedulaJuridica, restaurante)
}
