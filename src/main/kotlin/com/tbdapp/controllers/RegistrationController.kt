package com.tbdapp.controllers

import com.tbdapp.errors.TbdAppServerError
import com.tbdapp.models.*
import com.tbdapp.models.registration.RegistrationInput
import com.tbdapp.models.registration.RegistrationOutcome
import com.tbdapp.services.RegistrationService
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
                        schema = Schema(implementation = TbdAppServerError::class)
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
