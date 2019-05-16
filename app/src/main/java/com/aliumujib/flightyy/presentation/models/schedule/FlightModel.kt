package com.aliumujib.flightyy.presentation.models.schedule

import android.os.Parcelable
import com.aliumujib.flightyy.presentation.models.AirlineModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FlightModel(
    val arrivalModel: ArrivalDepartureModel,
    val departureModel: ArrivalDepartureModel,
    val airlineModel: AirlineModel,
    val flightNumber: Int,
    val stops: Int
) : Parcelable