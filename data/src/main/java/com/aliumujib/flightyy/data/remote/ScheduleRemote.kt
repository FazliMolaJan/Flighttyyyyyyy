package com.aliumujib.flightyy.data.remote

import com.aliumujib.flightyy.data.cache.AirlineCache
import com.aliumujib.flightyy.data.cache.AirportCache
import com.aliumujib.flightyy.data.contracts.ISchedulesRemote
import com.aliumujib.flightyy.data.model.schedule.ArrivalDepartureEntity
import com.aliumujib.flightyy.data.model.schedule.FlightEntity
import com.aliumujib.flightyy.data.model.schedule.ScheduleEntity
import com.aliumujib.flightyy.data.remote.api.ApiService
import com.aliumujib.flightyy.data.remote.models.Schedule
import io.reactivex.Observable
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import javax.inject.Inject

class ScheduleRemote @Inject constructor(
    var apiService: ApiService,
    var airportCache: AirportCache,
    var airlineCache: AirlineCache
) : ISchedulesRemote {

    override fun getSchedules(
        origin: String,
        destination: String,
        fromDateTime: String
    ): Observable<List<ScheduleEntity>> {
        return apiService.getCharacters(origin, destination, "2019-11-12").map {
            it.ScheduleResource.Schedule
        }.map {
            createScheduleEntities(it)
        }
    }

    fun createScheduleEntities(list: List<Schedule>): List<ScheduleEntity> {
        var scheduleList = mutableListOf<ScheduleEntity>()
        var dateTimeFormatter = SimpleDateFormat("YYYY-MM-DD'T'HH:MM")

        list.forEach {
            var flights = mutableListOf<FlightEntity>()
            it.Flight.forEach { flight ->
                var arrival = ArrivalDepartureEntity(
                    airportCache.get(flight.Arrival.AirportCode),
                    dateTimeFormatter.parse(flight.Arrival.ScheduledTimeLocal.DateTime)
                )
                var departure = ArrivalDepartureEntity(
                    airportCache.get(flight.Departure.AirportCode),
                    dateTimeFormatter.parse(flight.Departure.ScheduledTimeLocal.DateTime)
                )
                var airline = airlineCache.get(flight.MarketingCarrier.AirlineID)

                var flightEntity = FlightEntity(
                    arrival,
                    departure,
                    airline,
                    flight.MarketingCarrier.FlightNumber,
                    flight.Details.Stops.StopQuantity
                )

                flights.add(flightEntity)
            }

            var dtf = DateTimeFormat.forPattern("mm'H'ss'M'")
            var scheduleEntity = ScheduleEntity(flights, 0L)
            scheduleList.add(scheduleEntity)
        }

        return scheduleList
    }

}