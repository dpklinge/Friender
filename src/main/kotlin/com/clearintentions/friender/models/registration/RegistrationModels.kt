package com.clearintentions.friender.models.registration

import com.clearintentions.friender.errors.validation.ValidationError
import com.clearintentions.friender.models.*
import com.clearintentions.friender.validators.ValidEmail
import org.jetbrains.annotations.NotNull
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class RegistrationInput(
    @NotNull
    @Size(min = 2, max = 15, message = "Display name has invalid length - length should be between 2-15 characters")
    val displayName: String,
    @NotNull
    @NotBlank
    @Size(min = 12, message = "Password too short, must be at least 12 characters")
    val password: String,
    val passwordConfirmation: String,
    @ValidEmail
    val email: String,
    val phoneNumber: String,
    val gender: Gender,
    val age: Int
)
data class RegistrationOutcome(
    val didSucceed: Boolean,
    val errors: List<ValidationError>
)
