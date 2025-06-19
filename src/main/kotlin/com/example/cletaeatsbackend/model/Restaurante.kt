package com.example.cletaeatsbackend.model

import jakarta.persistence.*

@Entity
@Table(name = "restaurantes")
data class Restaurante(
    @Id
    val id: String,
    val cedulaJuridica: String,
    val nombre: String,
    val direccion: String,
    val tipoComida: String,
    val contrasena: String,
    @ElementCollection
    val combos: List<RestauranteCombo>
)

@Embeddable
data class RestauranteCombo(
    val numero: Int,
    val nombre: String,
    val precio: Double
)
