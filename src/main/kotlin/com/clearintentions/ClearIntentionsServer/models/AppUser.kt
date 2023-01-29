package com.clearintentions.ClearIntentionsServer.models

import java.util.UUID

data class AppUser(
    val id: UUID,
    val displayName: DisplayName,
    val password: Password,
    val email: Email,
    val phoneNumber: PhoneNumber,
    val account: UserAccount,
    val searchPreferences: UserSearchPreferences,
    val gender: Gender,
    val age: Age,
    val images: List<ByteArray>
)