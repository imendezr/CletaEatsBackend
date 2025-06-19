package com.example.cletaeatsbackend.repository

import com.example.cletaeatsbackend.model.Restaurante
import org.springframework.data.jpa.repository.JpaRepository

interface RestauranteRepository : JpaRepository<Restaurante, String> {
    fun findByCedulaJuridica(cedulaJuridica: String): Restaurante?
}
