package com.aliumujib.flightyy.data.repositories

import com.aliumujib.flightyy.data.DummyDataFactory
import com.aliumujib.flightyy.data.cache.airlines.AirlineCache
import com.aliumujib.flightyy.data.mapper.AirlineEntityMapper
import com.aliumujib.flightyy.data.model.AirlineEntity
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AirlinesRepositoryImplTest {

    lateinit var airlinesRepositoryImpl: AirlinesRepositoryImpl

    @Mock
    lateinit var airlinesCache: AirlineCache

    private var entityMapper: AirlineEntityMapper = AirlineEntityMapper()

    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)

        airlinesRepositoryImpl =
            AirlinesRepositoryImpl(
                    airlinesCache,
                    entityMapper
                )

    }

    @Test
    fun `check that calling getAirlineWithId on repository calls cache implementation`() {
        stubAirlineCacheResponse()
        airlinesRepositoryImpl.getAirlineWithId("LOS").test()
        verify(airlinesCache).get("LOS")
    }


    private fun stubAirlineCacheResponse(
        airline: AirlineEntity = DummyDataFactory.makeRandomAirlineEntity()) {
        whenever(airlinesCache.get(any()))
            .thenReturn(airline)
    }

}