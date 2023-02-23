package com.tbdapp.models

data class UserSearchPreferences(
    val intentions: List<Intention>,
    val desiredIntentions: List<Intention>,
    val desiredGenders: List<Gender>,
    val desiredMinAge: Age,
    val desiredMaxAge: Age,
    val desiredMaxDistance: Int
)
