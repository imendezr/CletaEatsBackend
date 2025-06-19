package com.example.cletaeatsbackend.service

import com.example.cletaeatsbackend.model.UserType
import com.example.cletaeatsbackend.repository.AdminRepository
import com.example.cletaeatsbackend.repository.ClienteRepository
import com.example.cletaeatsbackend.repository.RepartidorRepository
import com.example.cletaeatsbackend.repository.RestauranteRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val clienteRepository: ClienteRepository,
    private val repartidorRepository: RepartidorRepository,
    private val restauranteRepository: RestauranteRepository,
    private val adminRepository: AdminRepository
) {
    fun authenticateUser(cedula: String, contrasena: String): AuthResult {
        val cliente = clienteRepository.findByCedula(cedula)
        if (cliente != null && cliente.contrasena == contrasena) {
            return if (cliente.estado == "activo") {
                AuthResult.Success(UserType.ClienteUser(cliente))
            } else {
                AuthResult.Error("Cliente suspendido")
            }
        }

        val repartidor = repartidorRepository.findById(cedula).orElse(null)
        if (repartidor != null && repartidor.contrasena == contrasena) {
            return if (repartidor.amonestaciones >= 4) {
                AuthResult.Error("Repartidor con demasiadas amonestaciones")
            } else if (repartidor.estado == "inactivo") {
                AuthResult.Error("Repartidor inactivo")
            } else {
                AuthResult.Success(UserType.RepartidorUser(repartidor))
            }
        }

        val restaurante = restauranteRepository.findByCedulaJuridica(cedula)
        if (restaurante != null && restaurante.contrasena == contrasena) {
            return AuthResult.Success(UserType.RestauranteUser(restaurante))
        }

        val admin = adminRepository.findById(cedula).orElse(null)
        if (admin != null && admin.contrasena == contrasena) {
            return AuthResult.Success(UserType.AdminUser(admin))
        }

        return AuthResult.Error("Cédula o contraseña incorrecta")
    }
}

data class AuthRequest(
    val cedula: String,
    val contrasena: String
)

sealed class AuthResult {
    data class Success(val user: UserType) : AuthResult()
    data class Error(val message: String) : AuthResult()
}
