package com.clearintentions.friender.services

import com.clearintentions.friender.controllers.LocationUpdateRequest
import com.clearintentions.friender.models.UserLocation
import com.clearintentions.friender.repositories.UserLocationRepository
import kotlinx.coroutines.reactive.awaitFirst
import mu.KotlinLogging
import org.springframework.data.geo.Point
import org.springframework.stereotype.Service

@Service
class LocationService(val userLocationRepository: UserLocationRepository) {
    private val logger = KotlinLogging.logger {}

    suspend fun findUsersInRadius(source: Point, radius: Int) =
        userLocationRepository.findUsersInRadius(source.x, source.y, radius)

    suspend fun updateHomeLocation(updateRequest: LocationUpdateRequest): Int {
        val result = userLocationRepository.findById(updateRequest.id)
            .defaultIfEmpty(UserLocation(updateRequest.id, updateRequest.location, updateRequest.location))
            .doOnError {
                logger.warn { "Error saving home location update request $updateRequest: $it" }
                it.printStackTrace()
                throw it
            }
            .awaitFirst()
        println("Saving")
        return userLocationRepository.saveByCoordinates(result.id, result.currentLocation.x, result.currentLocation.y, updateRequest.location.x, updateRequest.location.y)
    }

    suspend fun updateCurrentLocation(updateRequest: LocationUpdateRequest): Int {
        val result = userLocationRepository.findById(updateRequest.id)
            .defaultIfEmpty(UserLocation(updateRequest.id, updateRequest.location, updateRequest.location))
            .doOnError {
                logger.warn { "Error saving home location update request $updateRequest: $it" }
                it.printStackTrace()
                throw it
            }
            .awaitFirst()
        return userLocationRepository.saveByCoordinates(result.id, updateRequest.location.x, updateRequest.location.y, result.homeLocation.x, result.homeLocation.y)
    }
}
