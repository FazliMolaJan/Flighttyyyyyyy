package com.aliumujib.flightyy.data.remote.api.retrofit

import com.aliumujib.flightyy.data.remote.models.LoginResponse
import com.aliumujib.flightyy.data.remote.models.SchedulesReponse
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("operations/schedules/{origin}/{destination}/{fromDateTime}")
    fun getCharacters(@Path("origin") origin: String, @Path("destination") destination: String,
                      @Path("fromDateTime") fromDateTime: String): Observable<SchedulesReponse>


    @POST("oauth/token")
    fun login(hashMap: HashMap<String, Any>): Observable<LoginResponse>

}