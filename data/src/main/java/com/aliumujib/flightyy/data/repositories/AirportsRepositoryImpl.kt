package com.aliumujib.flightyy.data.repositories

import com.aliumujib.flightyy.data.contracts.cache.ICache
import com.aliumujib.flightyy.data.mapper.AirportEntityMapper
import com.aliumujib.flightyy.data.model.AirportEntity
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.domain.repositories.airports.IAirportsRepository
import io.reactivex.Observable
import javax.inject.Inject

class AirportsRepositoryImpl @Inject constructor(
    var airportsCache: ICache<AirportEntity>,
    var airportsEntityMapper: AirportEntityMapper
) : IAirportsRepository {

    override fun searchAirports(): Observable<List<Airport>> {
        return Observable.just(airportsEntityMapper.mapFromEntityList(airportsCache.fetchAll()))
    }

}
