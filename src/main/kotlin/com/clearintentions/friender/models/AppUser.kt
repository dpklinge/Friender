package com.clearintentions.friender.models

import org.springframework.data.annotation.Id
import java.util.UUID

data class AppUser(
    val displayName: String,
    val password: String,
    val email: String,
    val phoneNumber: String,
    val gender: Gender,
    val age: Int,
    @Id
    var id: UUID? = null,
)
