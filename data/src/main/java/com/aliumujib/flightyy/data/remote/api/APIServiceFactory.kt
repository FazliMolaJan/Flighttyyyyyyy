package com.aliumujib.flightyy.data.remote.api

import com.aliumujib.flightyy.data.remote.models.Schedule
import com.aliumujib.flightyy.data.remote.models.ScheduleTypeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object APIServiceFactory {

    fun makeService(isDebug: Boolean, apiURL: String, apiKey:String): ApiService {
        val okHttpClient = makeOkHttpClient(makeLoggingInterceptor((isDebug)), makeDispatcher(), apiKey)
        return makeService(okHttpClient, apiURL, getGson())
    }

    private fun makeService(okHttpClient: OkHttpClient, apiURL: String, gson: Gson): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiURL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(ApiService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, dispatcher: Dispatcher, apiKey: String): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(AuthInterceptor(apiKey))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .dispatcher(dispatcher)
            .build()
    }


    fun getGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Schedule::class.java, ScheduleTypeAdapter() )
            .create()
    }


    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    private fun makeDispatcher(): Dispatcher {
        val dispatcher = Dispatcher()
        dispatcher.maxRequestsPerHost = 10
        return dispatcher
    }
}