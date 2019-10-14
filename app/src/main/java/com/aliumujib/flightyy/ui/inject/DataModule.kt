package com.aliumujib.flightyy.ui.inject

import com.aliumujib.flightyy.data.cache.airports.AirportCache
import com.aliumujib.flightyy.data.mapper.AirportEntityMapper
import com.aliumujib.flightyy.data.repositories.AirlinesRepositoryImpl
import com.aliumujib.flightyy.data.repositories.AirportsRepositoryImpl
import com.aliumujib.flightyy.data.repositories.SchedulesRepositoryImpl
import com.aliumujib.flightyy.domain.repositories.airlines.IAirlinesRepository
import com.aliumujib.flightyy.domain.repositories.airports.IAirportsRepository
import com.aliumujib.flightyy.domain.repositories.schedules.ISchedulesRepository
import dagger.Module
import dagger.Provides


@Module
 class DataModule {

    @Provides
     fun bindsAirportsRepository(airportCache: AirportCache, airportEntityMapper: AirportEntityMapper): IAirportsRepository{
        return AirportsRepositoryImpl(airportCache, airportEntityMapper)
    }

    @Provides
     fun bindsSchedulesRepository(repo: SchedulesRepositoryImpl): ISchedulesRepository{
        return repo
    }

    @Provides
     fun bindsAirlinesRepository(repo: AirlinesRepositoryImpl): IAirlinesRepository{
        return repo
    }

}