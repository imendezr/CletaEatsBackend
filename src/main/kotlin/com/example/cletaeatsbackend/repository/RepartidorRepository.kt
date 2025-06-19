package com.example.cletaeatsbackend.repository

import com.example.cletaeatsbackend.model.Repartidor
import org.springframework.data.jpa.repository.JpaRepository

interface RepartidorRepository : JpaRepository<Repartidor, String> {
    fun findByEstado(estado: String): List<Repartidor>
    fun findByAmonestaciones(amonestaciones: Int): List<Repartidor>
}
