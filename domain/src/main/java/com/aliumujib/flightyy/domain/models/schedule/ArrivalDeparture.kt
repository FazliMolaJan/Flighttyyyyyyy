package com.aliumujib.flightyy.domain.models.schedule

import com.aliumujib.flightyy.domain.models.Airport
import java.util.*

data class ArrivalDeparture(
    val airport: Airport,
    val scheduledTime: Date)