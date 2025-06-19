package com.example.cletaeatsbackend.controller

import com.example.cletaeatsbackend.model.UserType
import com.example.cletaeatsbackend.service.AuthRequest
import com.example.cletaeatsbackend.service.AuthResult
import com.example.cletaeatsbackend.service.AuthService
import com.fasterxml.jackson.annotation.JsonTypeId
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {
    @PostMapping("/login")
    fun authenticateUser(@RequestBody request: AuthRequest): AuthResultWrapper {
        val result = authService.authenticateUser(request.cedula, request.contrasena)
        return when (result) {
            is AuthResult.Success -> AuthResultWrapper("success", user = result.user)
            is AuthResult.Error -> AuthResultWrapper("error", message = result.message)
        }
    }
}

data class AuthResultWrapper(
    @JsonTypeId val type: String,
    val user: UserType? = null,
    val message: String? = null
)
