package com.example.cletaeatsbackend.controller

import com.example.cletaeatsbackend.service.AdminService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admins")
class AdminController(private val adminService: AdminService) {
    @GetMapping
    fun getAdmins() = adminService.getAdmins()
}
