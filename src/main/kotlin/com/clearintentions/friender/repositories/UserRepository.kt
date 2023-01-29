package com.clearintentions.friender.repositories

import com.clearintentions.friender.models.AppUser
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import java.util.*

interface UserRepository : ReactiveCrudRepository<AppUser, UUID> {
    suspend fun findAppUserByEmail(email: String): AppUser?
    suspend fun findAppUserByPhoneNumber(phoneNumber: String): AppUser?
}
