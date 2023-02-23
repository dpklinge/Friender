package com.clearintentions.friender.controllers

import com.clearintentions.friender.errors.ClearIntentionsServerError
import com.clearintentions.friender.models.*
import com.clearintentions.friender.models.registration.RegistrationInput
import com.clearintentions.friender.models.registration.RegistrationOutcome
import com.clearintentions.friender.services.RegistrationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
class RegistrationController(val registrationService: RegistrationService) {

    @Operation(
        summary = "Attempts to register a user with the provided information",
        responses = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = RegistrationOutcome::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ClearIntentionsServerError::class)
                    )
                ]
            )
        ]
    )
    @PostMapping("register")
    suspend fun registerUser(
        @Valid @RequestBody
        userRegistration: RegistrationInput
    ) = registrationService.registerUser(userRegistration)
}
