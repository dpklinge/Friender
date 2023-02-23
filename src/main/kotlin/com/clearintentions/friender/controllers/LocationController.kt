package com.clearintentions.friender.controllers

import com.clearintentions.friender.models.UserLocation
import com.clearintentions.friender.services.LocationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.data.geo.Point
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class LocationController(val locationService: LocationService) {

    @Operation(
        summary = "Updates a user's home location",
        responses = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = UserLocation::class)
                    )
                ]
            )
        ]
    )
    @PostMapping("/updateHomeLocation")
    suspend fun updateHomeLocation(
        @RequestBody updateRequest: LocationUpdateRequest
    ) = ResponseEntity.ok().body(locationService.updateHomeLocation(updateRequest))

    @Operation(
        summary = "Updates a user's current location (used to search)",
        responses = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = UserLocation::class)
                    )
                ]
            )
        ]
    )
    @PostMapping("/updateCurrentLocation")
    suspend fun updateCurrentLocation(
        @RequestBody updateRequest: LocationUpdateRequest
    ) = ResponseEntity.ok().body(locationService.updateCurrentLocation(updateRequest))
}

data class LocationUpdateRequest(
    val id: UUID,
    val location: Point
)
