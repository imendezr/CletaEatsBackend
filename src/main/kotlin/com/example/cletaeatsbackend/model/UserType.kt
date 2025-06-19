package com.example.cletaeatsbackend.model

sealed class UserType {
    data class ClienteUser(val cliente: Cliente) : UserType()
    data class RepartidorUser(val repartidor: Repartidor) : UserType()
    data class RestauranteUser(val restaurante: Restaurante) : UserType()
    data class AdminUser(val admin: Admin) : UserType()
}
