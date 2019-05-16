package com.aliumujib.flightyy.ui.inject

import android.content.Context
import com.aliumujib.flightyy.data.cache.AirlineCache
import com.aliumujib.flightyy.data.cache.AirportCache
import dagger.Module
import dagger.Provides


@Module
class CacheModule {

    @Provides
    fun bindsAirportCache(context: Context): AirportCache {
        return AirportCache(context)
    }

    @Provides
    fun bindsAirlineCache(context: Context): AirlineCache {
        return AirlineCache(context)
    }

}