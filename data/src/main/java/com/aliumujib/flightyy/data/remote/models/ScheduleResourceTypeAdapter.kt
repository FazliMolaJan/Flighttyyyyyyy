package com.aliumujib.flightyy.data.remote.models

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class ScheduleResourceTypeAdapter : JsonDeserializer<ScheduleResource> {


    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ScheduleResource? {
        var scheduleRes: ScheduleResource? = null
        val schedules = mutableListOf<Schedule>()
        json?.asJsonObject?.let {
            var scheduleResource = it.get("Schedule")
            if (scheduleResource is JsonArray) {
                val listType = object : TypeToken<List<Schedule>?>() {}.type
                schedules.addAll(Gson().fromJson(scheduleResource, listType))
            } else {
                val listType = object : TypeToken<Schedule?>() {}.type
                val flight = Gson().fromJson<Schedule>(scheduleResource, listType)
                schedules.add(flight)
            }
            scheduleRes = ScheduleResource(schedules)
        }
        return scheduleRes
    }


}