package com.example.cletaeatsbackend.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "clientes")
data class Cliente(
    @Id
    val id: String,
    val cedula: String,
    val nombre: String,
    val direccion: String,
    val telefono: String,
    val correo: String,
    val estado: String, // "activo" or "suspendido"
    val contrasena: String,
    val numeroTarjeta: String
)
