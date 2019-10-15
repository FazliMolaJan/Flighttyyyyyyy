package com.aliumujib.flightyy.ui.inject

import com.aliumujib.flightyy.BuildConfig
import com.aliumujib.flightyy.data.cache.airlines.AirlineCache
import com.aliumujib.flightyy.data.cache.airports.AirportCache
import com.aliumujib.flightyy.data.contracts.remote.IAuthRemote
import com.aliumujib.flightyy.data.contracts.remote.ISchedulesRemote
import com.aliumujib.flightyy.data.remote.api.retrofit.APIServiceFactory
import com.aliumujib.flightyy.data.remote.api.retrofit.ApiService
import com.aliumujib.flightyy.data.remote.api.utils.AccessTokenAuthenticator
import com.aliumujib.flightyy.data.remote.api.utils.AuthInterceptor
import com.aliumujib.flightyy.data.remote.api.utils.TokenRefresher
import com.aliumujib.flightyy.data.remote.impl.AuthRemote
import com.aliumujib.flightyy.data.remote.impl.ScheduleRemote
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
class RemoteModule {

    @Provides
    fun providesAuthRemote(remote: AuthRemote): IAuthRemote {
        return remote
    }

    @Provides
    fun providesAuthService(retrofit: Retrofit): ApiService {
        return APIServiceFactory.makeApiService(retrofit)
    }

    @Provides
    fun providesTokenRefresher(gson: Gson): TokenRefresher {
        return TokenRefresher(gson, BuildConfig.API_URL)
    }


    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return APIServiceFactory.makeRetrofitInstance(okHttpClient, BuildConfig.API_URL, gson)
    }

    @Provides
    fun provideOKHTTPInstance(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        dispatcher: Dispatcher,
        authInterceptor: AuthInterceptor,
        accessTokenAuthenticator: AccessTokenAuthenticator
    ): OkHttpClient {
        return APIServiceFactory.makeOkHttpClient(
            httpLoggingInterceptor,
            dispatcher,
            authInterceptor,
            accessTokenAuthenticator
        )
    }

    @Provides
    fun provideGson(): Gson {
        return APIServiceFactory.getGson()
    }

    @Provides
    fun provideDispatcher(): Dispatcher {
        return APIServiceFactory.makeDispatcher()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return APIServiceFactory.makeLoggingInterceptor(BuildConfig.DEBUG)
    }



    @Provides
    fun bindsSchedulesRemote(
        apiService: ApiService,
        airportCache: AirportCache,
        airlineCache: AirlineCache
    ): ISchedulesRemote {
        return ScheduleRemote(
            apiService,
            airportCache,
            airlineCache
        )
    }

}