package com.aliumujib.flightyy.data.repositories

import com.aliumujib.flightyy.data.DummyDataFactory
import com.aliumujib.flightyy.data.cache.airports.AirportCache
import com.aliumujib.flightyy.data.mapper.AirportEntityMapper
import com.aliumujib.flightyy.data.model.AirportEntity
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AirportsRepositoryImplTest {

    lateinit var airportsRepositoryImpl: AirportsRepositoryImpl

    @Mock
    lateinit var airportsCache: AirportCache

    var entityMapper: AirportEntityMapper = AirportEntityMapper()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        airportsRepositoryImpl =
                AirportsRepositoryImpl(
                    airportsCache,
                    entityMapper
                )

    }

    @Test
    fun `check that calling searchAirports on repository calls cache implementation`() {
        stubAirportCacheResponse()
        airportsRepositoryImpl.searchAirports().test()
        verify(airportsCache).fetchAll()
    }


    private fun stubAirportCacheResponse(
        list: List<AirportEntity> = DummyDataFactory.makeAirportEntities(10)
    ) {
        whenever(airportsCache.fetchAll())
            .thenReturn(list)
    }

}