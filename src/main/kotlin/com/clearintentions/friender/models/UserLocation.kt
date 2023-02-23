package com.clearintentions.friender.models

import org.springframework.data.annotation.Id
import org.springframework.data.geo.Point
import java.util.UUID

data class UserLocation(
    @Id
    val id: UUID,
    val latestLocation: Point,
    val homeLocation: Point?
)
