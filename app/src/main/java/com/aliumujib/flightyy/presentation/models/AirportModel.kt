package com.aliumujib.flightyy.presentation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AirportModel(
    val carriers: String,
    val city: String,
    val code: String,
    val country: String,
    val direct_flights: String,
    val elev: String,
    val email: String,
    val icao: String,
    val lat: String,
    val lon: String,
    val name: String,
    val phone: String,
    val runway_length: String,
    val state: String,
    val type: String,
    val tz: String,
    val url: String,
    val woeid: String
) : Parcelable {
    fun location():String = "$city, $state, $country"
}