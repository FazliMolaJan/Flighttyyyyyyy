package com.aliumujib.flightyy.data.contracts

import com.aliumujib.flightyy.data.model.schedule.ScheduleEntity
import io.reactivex.Observable

interface ISchedulesRemote{

    fun getSchedules( origin: String, destination: String,
                       fromDateTime: String): Observable<List<ScheduleEntity>>

}