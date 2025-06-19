package com.example.cletaeatsbackend.repository

import com.example.cletaeatsbackend.model.Cliente
import org.springframework.data.jpa.repository.JpaRepository

interface ClienteRepository : JpaRepository<Cliente, String> {
    fun findByCedula(cedula: String): Cliente?
    fun findByEstado(estado: String): List<Cliente>
}
