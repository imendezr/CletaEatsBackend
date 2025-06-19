package com.example.cletaeatsbackend.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = UserType.ClienteUser::class, name = "cliente"),
    JsonSubTypes.Type(value = UserType.RepartidorUser::class, name = "repartidor"),
    JsonSubTypes.Type(value = UserType.RestauranteUser::class, name = "restaurante"),
    JsonSubTypes.Type(value = UserType.AdminUser::class, name = "admin")
)
sealed class UserType {
    data class ClienteUser(val cliente: Cliente) : UserType()
    data class RepartidorUser(val repartidor: Repartidor) : UserType()
    data class RestauranteUser(val restaurante: Restaurante) : UserType()
    data class AdminUser(val admin: Admin) : UserType()
}
