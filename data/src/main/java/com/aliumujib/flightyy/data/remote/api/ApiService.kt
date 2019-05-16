package com.aliumujib.flightyy.data.remote.api

import com.aliumujib.flightyy.data.remote.models.SchedulesReponse
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("operations/schedules/{origin}/{destination}/{fromDateTime}")
    fun getCharacters(@Query("origin") origin: String,@Query("destination") destination: String,
                      @Query("fromDateTime") fromDateTime: String): Observable<SchedulesReponse>

}