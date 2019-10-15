package com.aliumujib.flightyy.data.remote.api.retrofit

import com.aliumujib.flightyy.data.remote.models.LoginResponse
import com.aliumujib.flightyy.data.remote.models.SchedulesReponse
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @GET("operations/schedules/{origin}/{destination}/{fromDateTime}")
    fun getCharacters(@Path("origin") origin: String, @Path("destination") destination: String,
                      @Path("fromDateTime") fromDateTime: String): Observable<SchedulesReponse>


    @FormUrlEncoded
    @POST("oauth/token")
    fun login(@FieldMap hashMap: HashMap<String,@JvmSuppressWildcards Any>): Observable<LoginResponse>

    @FormUrlEncoded
    @POST("oauth/token")
    fun nonRxLogin(@FieldMap hashMap: HashMap<String,@JvmSuppressWildcards Any>): Call<LoginResponse>

}