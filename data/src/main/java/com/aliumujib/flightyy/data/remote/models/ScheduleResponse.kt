package com.aliumujib.flightyy.data.remote.models

data class SchedulesReponse(
    val ScheduleResource: ScheduleResource
)

data class ScheduleResource(
    val Schedule: List<Schedule>
)

data class Schedule(
    val Flight: List<Flight>,
    val TotalJourney: TotalJourney
)

data class Flight(
    val Arrival: Arrival,
    val Departure: Departure,
    val Details: Details,
    val Equipment: Equipment,
    val MarketingCarrier: MarketingCarrier,
    val OperatingCarrier: OperatingCarrier
)

data class Equipment(
    val AircraftCode: String
)

data class Details(
    val DatePeriod: DatePeriod,
    val DaysOfOperation: Int,
    val Stops: Stops
)

data class DatePeriod(
    val Effective: String,
    val Expiration: String
)

data class Stops(
    val StopQuantity: Int
)

data class OperatingCarrier(
    val AirlineID: String
)

data class Arrival(
    val AirportCode: String,
    val ScheduledTimeLocal: ScheduledTimeLocal,
    val Terminal: Terminal
)

data class ScheduledTimeLocal(
    val DateTime: String
)

data class Terminal(
    val Name: Int
)

data class Departure(
    val AirportCode: String,
    val ScheduledTimeLocal: ScheduledTimeLocal
)

data class MarketingCarrier(
    val AirlineID: String,
    val FlightNumber: Int
)

data class TotalJourney(
    val Duration: String
){
}