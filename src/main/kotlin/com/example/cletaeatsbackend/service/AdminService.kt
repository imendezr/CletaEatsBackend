package com.example.cletaeatsbackend.service

import com.example.cletaeatsbackend.model.Admin
import com.example.cletaeatsbackend.repository.AdminRepository
import org.springframework.stereotype.Service

@Service
class AdminService(private val adminRepository: AdminRepository) {
    fun getAdmins(): List<Admin> = adminRepository.findAll()
}
