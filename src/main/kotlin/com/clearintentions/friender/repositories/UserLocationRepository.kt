package com.clearintentions.friender.repositories

import com.clearintentions.friender.models.AppUser
import com.clearintentions.friender.models.UserLocation
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import java.util.*

interface UserLocationRepository : R2dbcRepository<UserLocation, UUID> {
    @Query("SELECT a.* FROM user_location u JOIN app_user a ON u.id = a.id AND ST_DWithin(u.home_location, ST_SetSRID(ST_MakePoint(:x, :y), 4326), :radius * 1609.34)")
    suspend fun findUsersInRadius(x: Double, y: Double, radius: Int): Flow<AppUser>

    @Query(
        "INSERT INTO user_location(id, current_location, home_location) " +
            "VALUES(" +
            ":id, " +
            "ST_SetSRID(ST_MakePoint(:currentX, :currentY), 4326), " +
            "ST_SetSRID(ST_MakePoint(:homeX, :homeY), 4326)" +
            ") " +
            "ON CONFLICT (id) DO UPDATE SET " +
            "current_location = ST_SetSRID(ST_MakePoint(:currentX, :currentY), 4326), " +
            "home_location = ST_SetSRID(ST_MakePoint(:homeX, :homeY), 4326) " +
            "WHERE user_location.id = :id"
    )
    suspend fun saveByCoordinates(id: UUID, currentX: Double, currentY: Double, homeX: Double, homeY: Double): Int
}
