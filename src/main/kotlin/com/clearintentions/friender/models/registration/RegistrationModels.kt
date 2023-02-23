package com.clearintentions.friender.models.registration

import com.clearintentions.friender.errors.validation.ValidationError
import com.clearintentions.friender.models.*
import com.clearintentions.friender.validators.ValidEmail
import com.clearintentions.friender.validators.ValidPhoneNumber
import javax.validation.constraints.*

data class RegistrationInput(
    @field:Size(min = 2, max = 15, message = "Display name has invalid length - length should be between 2-15 characters")
    val displayName: String,
    @field:Size(min = 12, message = "Password too short, must be at least 12 characters")
    val password: String,
    val passwordConfirmation: String,
    @ValidEmail
    val email: String,
    @ValidPhoneNumber
    val phoneNumber: String,
    val gender: Gender,
    @field:Max(value = 130, message = "That age seems pretty unlikely")
    @field:Min(value = 18, message = "Adults only for now")
    val age: Int
)
data class RegistrationOutcome(
    val didSucceed: Boolean,
    val errors: List<ValidationError>
)
