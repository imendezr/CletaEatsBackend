package com.example.cletaeatsbackend.model

import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "repartidores")
data class Repartidor(
    @Id
    val id: String,
    val cedula: String,
    val nombre: String,
    val direccion: String,
    val telefono: String,
    val correo: String,
    val estado: String, // "disponible", "ocupado", "inactivo"
    val kmRecorridosDiarios: Double,
    val costoPorKmHabiles: Double,
    val costoPorKmFeriados: Double,
    val amonestaciones: Int,
    @ElementCollection
    val quejas: List<String>,
    val contrasena: String,
    val numeroTarjeta: String? = null
)
