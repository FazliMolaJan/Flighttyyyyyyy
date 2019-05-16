package com.aliumujib.flightyy.data.model.schedule

import com.aliumujib.flightyy.data.model.AirportEntity
import com.aliumujib.flightyy.domain.models.Airport
import java.util.*

data class ArrivalDepartureEntity(
    val airport: AirportEntity,
    val scheduledTime: Date
)