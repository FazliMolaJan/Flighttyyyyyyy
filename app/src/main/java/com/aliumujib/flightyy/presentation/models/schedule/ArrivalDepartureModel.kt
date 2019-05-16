package com.aliumujib.flightyy.presentation.models.schedule

import com.aliumujib.flightyy.presentation.models.AirportModel
import java.text.SimpleDateFormat
import java.util.*

data class ArrivalDepartureModel(
    val airportModel: AirportModel,
    val scheduledTime: Date){

    val formattedTime = SimpleDateFormat("HH:MM").format(scheduledTime)

}