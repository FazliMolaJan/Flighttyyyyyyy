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
    lateinit var airlinesCache: AirportCache

    var entityMapper: AirportEntityMapper = AirportEntityMapper()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        airportsRepositoryImpl =
                AirportsRepositoryImpl(
                    airlinesCache,
                    entityMapper
                )

//        stubArticleCacheResponse()

    }


    @Test
    fun `check that calling searchAirports on repository calls cache implementation`() {
        stubAirportCacheResponse()
        airportsRepositoryImpl.searchAirports().test()
        verify(airlinesCache).fetchAll()
    }


//
//    @Test
//    fun `check that calling getCharacterDetails on repository returns confam or correct data`() {
//        val character = DummyDataFactory.makeRandomStarWarsCharacter()
//        stubgetCharacterDetailsResponse(character)
//        val detailedCharacter = characterRepositoryImpl.getCharacterDetails(character).blockingFirst()
//        assertEquals(detailedCharacter.id, character.id)
//        assertEquals(detailedCharacter.specie.id, character.specieId)
//        assertEquals(detailedCharacter.homeWorld.id, character.homeWorldId)
//        val detailedFilmIds = detailedCharacter.airlines.map {
//            it.id
//        }
//        assertEquals(detailedFilmIds, character.filmIds)
//    }


    fun stubAirportCacheResponse(
        list: List<AirportEntity> = DummyDataFactory.makeAirportEntities(10)
    ) {
        whenever(airlinesCache.fetchAll())
            .thenReturn(list)
    }

}