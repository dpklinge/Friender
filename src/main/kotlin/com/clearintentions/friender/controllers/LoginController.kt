package com.clearintentions.friender.controllers

import com.clearintentions.friender.errors.ClearIntentionsServerError
import com.clearintentions.friender.models.AppUser
import com.clearintentions.friender.services.LoginService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(val loginService: LoginService) {

    @GetMapping("test")
    fun test() = "test"

    @Operation(
        summary = "Attempts to log in with provided username and password",
        responses = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = AppUser::class)
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
    @PostMapping("/login")
    suspend fun loginUser(@RequestBody loginPacket: LoginPacket) =
        loginService.verifyLoginInfo(loginPacket.username, loginPacket.password)
            .fold({ ResponseEntity.status(it.errorCode.statusCode).body(it) }, { ResponseEntity.ok().body(it) })
}

data class LoginPacket(val username: String, val password: String)
