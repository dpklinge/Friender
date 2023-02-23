package com.clearintentions.friender.services

import com.clearintentions.friender.controllers.LocationUpdateRequest
import com.clearintentions.friender.models.UserLocation
import com.clearintentions.friender.repositories.UserLocationRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class LocationService(val userLocationRepository: UserLocationRepository) {
    private val logger = KotlinLogging.logger {}
    suspend fun updateHomeLocation(updateRequest: LocationUpdateRequest) = userLocationRepository.findById(updateRequest.id)
        .defaultIfEmpty(UserLocation(updateRequest.id, updateRequest.location, updateRequest.location))
        .doOnError {
            logger.warn { "Error saving home location update request $updateRequest: $it" }
            it.printStackTrace()
            throw it
        }.doOnSuccess { userLocationRepository.save(UserLocation(it.id, it.latestLocation, updateRequest.location)) }
        .awaitFirstOrNull()

    suspend fun updateCurrentLocation(updateRequest: LocationUpdateRequest) = userLocationRepository.findById(updateRequest.id)
        .defaultIfEmpty(UserLocation(updateRequest.id, updateRequest.location, null))
        .doOnError {
            logger.warn { "Error saving home location update request $updateRequest: $it" }
            it.printStackTrace()
            throw it
        }
        .doOnSuccess { userLocationRepository.save(UserLocation(it.id, updateRequest.location, it.homeLocation)) }
        .awaitFirstOrNull()
}
