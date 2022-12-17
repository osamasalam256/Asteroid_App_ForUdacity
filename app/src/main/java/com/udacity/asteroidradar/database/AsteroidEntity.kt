package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.AsteroDomain


@Entity
data class AsteroidEntity(
    @PrimaryKey val id: Long, @ColumnInfo val codename: String, @ColumnInfo val closeApproachDate: String,
    @ColumnInfo val absoluteMagnitude: Double, @ColumnInfo val estimatedDiameter: Double,
    @ColumnInfo val relativeVelocity: Double, @ColumnInfo val distanceFromEarth: Double,
    @ColumnInfo val isPotentiallyHazardous: Boolean)

fun List<AsteroidEntity>.asDomainModel():List<AsteroDomain>{
    return map {
        AsteroDomain(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}