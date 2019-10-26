package com.aliumujib.flightyy.data.remote.api.retrofit

import com.aliumujib.flightyy.data.remote.models.LoginResponse
import com.aliumujib.flightyy.data.remote.models.SchedulesReponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @GET("operations/schedules/{origin}/{destination}/{fromDateTime}")
    fun getFlights(@Path("origin") origin: String, @Path("destination") destination: String,
                   @Path("fromDateTime") fromDateTime: String): Single<SchedulesReponse>


    @FormUrlEncoded
    @POST("oauth/token")
    fun login(@FieldMap hashMap: HashMap<String,@JvmSuppressWildcards Any>): Single<LoginResponse>

    @FormUrlEncoded
    @POST("oauth/token")
    fun syncronousLogin(@FieldMap hashMap: HashMap<String,@JvmSuppressWildcards Any>): Call<LoginResponse>

}