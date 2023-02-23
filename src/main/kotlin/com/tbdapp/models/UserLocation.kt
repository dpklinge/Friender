package com.tbdapp.models

import org.springframework.data.annotation.Id
import org.springframework.data.geo.Point
import java.util.UUID

data class UserLocation(
    @Id
    val id: UUID,
    val currentLocation: Point,
    val homeLocation: Point
)
