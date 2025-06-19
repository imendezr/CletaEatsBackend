package com.example.cletaeatsbackend.service

import com.example.cletaeatsbackend.model.Repartidor
import com.example.cletaeatsbackend.repository.RepartidorRepository
import org.springframework.stereotype.Service

@Service
class RepartidorService(private val repartidorRepository: RepartidorRepository) {
    fun getRepartidores(): List<Repartidor> = repartidorRepository.findAll()
    fun getRepartidoresSinAmonestaciones(): List<Repartidor> = repartidorRepository.findByAmonestaciones(0)
    fun registerRepartidor(repartidor: Repartidor): Boolean {
        if (repartidorRepository.findById(repartidor.id).isPresent) return false
        repartidorRepository.save(repartidor)
        return true
    }

    fun updateRepartidor(cedula: String, repartidor: Repartidor): Boolean {
        val existing = repartidorRepository.findById(cedula).orElse(null) ?: return false
        repartidorRepository.save(repartidor.copy(id = existing.id))
        return true
    }

    fun addQueja(repartidorId: String, queja: String, addAmonestacion: Boolean): Boolean {
        val repartidor = repartidorRepository.findById(repartidorId).orElse(null) ?: return false
        val updatedQuejas = if (queja.isNotBlank()) repartidor.quejas + queja else repartidor.quejas
        val updatedAmonestaciones = repartidor.amonestaciones + if (addAmonestacion) 1 else 0
        val updatedRepartidor = repartidor.copy(
            quejas = updatedQuejas,
            amonestaciones = updatedAmonestaciones,
            estado = if (updatedAmonestaciones >= 4) "inactivo" else repartidor.estado
        )
        repartidorRepository.save(updatedRepartidor)
        return true
    }

    fun resetRepartidoresEstado(): Boolean {
        val repartidores = repartidorRepository.findAll().map {
            if (it.amonestaciones < 4) it.copy(estado = "disponible") else it
        }
        repartidorRepository.saveAll(repartidores)
        return true
    }
}
