package com.aliumujib.flightyy.ui.inject

import com.aliumujib.flightyy.BuildConfig
import com.aliumujib.flightyy.data.cache.AirlineCache
import com.aliumujib.flightyy.data.cache.AirportCache
import com.aliumujib.flightyy.data.contracts.ISchedulesRemote
import com.aliumujib.flightyy.data.remote.ScheduleRemote
import com.aliumujib.flightyy.data.remote.api.APIServiceFactory
import com.aliumujib.flightyy.data.remote.api.ApiService
import dagger.Module
import dagger.Provides

@Module
class RemoteModule {

    @Provides
    fun provideService(): ApiService {
        return APIServiceFactory.makeService(BuildConfig.DEBUG, BuildConfig.API_URL)
    }


    @Provides
    fun bindsSchedulesRemote(
        apiService: ApiService,
        airportCache: AirportCache,
        airlineCache: AirlineCache
    ): ISchedulesRemote {
        return ScheduleRemote(apiService, airportCache, airlineCache)
    }

}