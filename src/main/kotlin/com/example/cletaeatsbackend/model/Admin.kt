package com.example.cletaeatsbackend.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "admins")
data class Admin(
    @Id
    val id: String,
    val nombreUsuario: String,
    val contrasena: String
)
