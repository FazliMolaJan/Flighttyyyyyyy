package com.aliumujib.flightyy.data.remote.api.retrofit

import com.aliumujib.flightyy.data.remote.api.utils.AccessTokenAuthenticator
import com.aliumujib.flightyy.data.remote.api.utils.AuthInterceptor
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

    fun makeApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    fun makeSimplerRetrofitInstance(apiURL: String, gson: Gson): Retrofit {

        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(HttpLoggingInterceptor())
        httpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(60, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(apiURL)
            .client(httpClientBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun makeRetrofitInstance(okHttpClient: OkHttpClient, apiURL: String, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiURL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun makeOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        dispatcher: Dispatcher,
        authInterceptor: AuthInterceptor,
        accessTokenAuthenticator: AccessTokenAuthenticator
    ): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(httpLoggingInterceptor)
        httpClientBuilder.addInterceptor(authInterceptor)
        httpClientBuilder.authenticator(accessTokenAuthenticator)
        httpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.dispatcher(dispatcher)

        return httpClientBuilder.build()
    }


    fun getGson(): Gson {
        return GsonBuilder()
            .create()
    }


    fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    fun makeDispatcher(): Dispatcher {
        val dispatcher = Dispatcher()
        dispatcher.maxRequestsPerHost = 10
        return dispatcher
    }
}