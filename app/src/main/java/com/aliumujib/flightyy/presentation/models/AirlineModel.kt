package com.aliumujib.flightyy.presentation.models


data class AirlineModel(
    val active: String,
    val alias: String,
    val callsign: String,
    val country: String,
    val iata: String,
    val icao: String,
    val id: String,
    val name: String
)