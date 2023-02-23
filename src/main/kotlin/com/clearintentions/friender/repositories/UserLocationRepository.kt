package com.clearintentions.friender.repositories

import com.clearintentions.friender.models.UserLocation
import org.springframework.data.r2dbc.repository.R2dbcRepository
import java.util.*

interface UserLocationRepository : R2dbcRepository<UserLocation, UUID>
