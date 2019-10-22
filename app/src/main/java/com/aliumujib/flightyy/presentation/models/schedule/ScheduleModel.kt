package com.aliumujib.flightyy.presentation.models.schedule

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScheduleModel(
    val flightModels: List<FlightModel>,
    val duration: Long
) : Parcelable
