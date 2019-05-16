package com.aliumujib.flightyy.data.remote.models

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class ScheduleTypeAdapter : JsonDeserializer<Schedule> {


    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Schedule? {
        var schedule: Schedule? = null
        json?.asJsonObject?.let {
            var totalJ = it.get("TotalJourney").asJsonObject
            var totalJourney = TotalJourney(totalJ.get("Duration").asString)
            var flights = mutableListOf<Flight>()
            it.get("Flight").let { flightelement ->
                if (flightelement is JsonArray) {
                    val listType = object : TypeToken<List<Flight>?>() {}.type
                    flights.addAll(Gson().fromJson(flightelement, listType))
                } else {
                    val listType = object : TypeToken<Flight?>() {}.type
                    val flight = Gson().fromJson<Flight>(flightelement, listType)
                    flights.add(flight)
                }
            }
            schedule = Schedule(flights, totalJourney)
        }
        return schedule
    }


}