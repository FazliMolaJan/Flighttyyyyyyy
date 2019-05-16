package com.aliumujib.flightyy.presentation.models.schedule

import android.os.Parcelable
import com.aliumujib.flightyy.presentation.models.AirportModel
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class ArrivalDepartureModel(
    val airportModel: AirportModel,
    val scheduledTime: Date) : Parcelable {

    @IgnoredOnParcel
    val formattedTime = SimpleDateFormat("HH:MM").format(scheduledTime)

}