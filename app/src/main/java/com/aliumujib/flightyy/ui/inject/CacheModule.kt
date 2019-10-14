package com.aliumujib.flightyy.ui.inject

import android.content.Context
import com.aliumujib.flightyy.data.cache.airlines.AirlineCache
import com.aliumujib.flightyy.data.cache.airports.AirportCache
import com.aliumujib.flightyy.data.cache.auth.AuthTokenManager
import com.aliumujib.flightyy.data.cache.auth.UserLoginManager
import com.aliumujib.flightyy.data.contracts.cache.IAuthTokenManager
import com.aliumujib.flightyy.data.contracts.cache.IUserLoginManager
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

    @Provides
    fun providesTokenRefresher(context: Context): IAuthTokenManager {
        return AuthTokenManager(context)
    }

    @Provides
    fun providesUserLoginManager(context: Context): IUserLoginManager {
        return UserLoginManager(context)
    }

}