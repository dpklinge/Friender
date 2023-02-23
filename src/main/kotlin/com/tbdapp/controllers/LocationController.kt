package com.tbdapp.controllers

import com.tbdapp.models.AppUser
import com.tbdapp.models.UserLocation
import com.tbdapp.services.LocationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.data.geo.Point
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class LocationController(val locationService: LocationService) {

    @Operation(
        summary = "Get users in radius",
        responses = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(
                            schema = Schema(implementation = AppUser::class)
                        )
                    )
                ]
            )
        ]
    )
    @GetMapping("/findUsers")
    suspend fun updateHomeLocation(
        @RequestParam("x") x: Double,
        @RequestParam("y") y: Double,
        @RequestParam("radius") radius: Int
    ) = locationService.findUsersInRadius(Point(x, y), radius)

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
    ) = locationService.updateHomeLocation(updateRequest)

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
    ) = locationService.updateCurrentLocation(updateRequest)
}
data class LocationUpdateRequest(
    val id: UUID,
    val location: Point
)
