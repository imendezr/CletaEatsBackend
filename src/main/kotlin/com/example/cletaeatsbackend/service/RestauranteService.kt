package com.example.cletaeatsbackend.service

import com.example.cletaeatsbackend.model.Restaurante
import com.example.cletaeatsbackend.repository.RestauranteRepository
import org.springframework.stereotype.Service

@Service
class RestauranteService(private val restauranteRepository: RestauranteRepository) {
    fun getRestaurantes(): List<Restaurante> = restauranteRepository.findAll()
    fun registerRestaurante(restaurante: Restaurante): Boolean {
        if (restauranteRepository.findByCedulaJuridica(restaurante.cedulaJuridica) != null) return false
        restauranteRepository.save(restaurante)
        return true
    }

    fun updateRestaurante(cedulaJuridica: String, restaurante: Restaurante): Boolean {
        val existing = restauranteRepository.findByCedulaJuridica(cedulaJuridica) ?: return false
        restauranteRepository.save(restaurante.copy(id = existing.id))
        return true
    }
}
