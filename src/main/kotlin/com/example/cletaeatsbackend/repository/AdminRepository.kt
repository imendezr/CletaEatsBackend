package com.example.cletaeatsbackend.repository

import com.example.cletaeatsbackend.model.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdminRepository : JpaRepository<Admin, String>
