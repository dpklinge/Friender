package com.tbdapp.repositories

import com.tbdapp.models.AppUser
import org.springframework.data.r2dbc.repository.R2dbcRepository
import java.util.*

interface UserRepository : R2dbcRepository<AppUser, UUID> {
    suspend fun findAppUserByEmail(email: String): AppUser?
    suspend fun findAppUserByPhoneNumber(phoneNumber: String): AppUser?
}
