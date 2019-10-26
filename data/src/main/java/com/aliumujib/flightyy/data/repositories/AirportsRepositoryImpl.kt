package com.aliumujib.flightyy.data.repositories

import com.aliumujib.flightyy.data.contracts.cache.ICache
import com.aliumujib.flightyy.data.mapper.AirportEntityMapper
import com.aliumujib.flightyy.data.model.AirportEntity
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.domain.repositories.airports.IAirportsRepository
import io.reactivex.Single
import javax.inject.Inject

class AirportsRepositoryImpl @Inject constructor(
    private val airportsCache: ICache<AirportEntity>,
    private val airportsEntityMapper: AirportEntityMapper
) : IAirportsRepository {

    override fun searchAirports(): Single<List<Airport>> {
        return Single.just(airportsEntityMapper.mapFromEntityList(airportsCache.fetchAll()))
    }

}
