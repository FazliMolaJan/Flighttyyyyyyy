package com.aliumujib.flightyy.data.remote.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object APIServiceFactory {

    fun makeService(isDebug: Boolean, apiURL: String): ApiService {
        val okHttpClient = makeOkHttpClient(makeLoggingInterceptor((isDebug)), makeDispatcher())
        return makeService(okHttpClient, apiURL)
    }

    private fun makeService(okHttpClient: OkHttpClient, apiURL: String): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiURL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, dispatcher: Dispatcher): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(AuthInterceptor())
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .dispatcher(dispatcher)
            .build()
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