package com.aliumujib.flightyy.data.mapper

import com.aliumujib.flightyy.data.DummyDataFactory
import com.aliumujib.flightyy.data.model.AirportEntity
import com.aliumujib.flightyy.domain.models.Airport
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class AirportEntityMapperTest {

    private val airportEntityMapper = AirportEntityMapper()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun `check that AirportEntity mapped to Airport maps correct info`() {
        val airportEntity = DummyDataFactory.makeRandomAirportEntity()
        val airport = airportEntityMapper.mapFromEntity(airportEntity)

        assertEquality(airportEntity, airport)
    }


    @Test
    fun `check that Airport mapped from AirportEntity maps correct info`() {
        val character :Airport= DummyDataFactory.makeRandomAirport()
        val characterEntity = airportEntityMapper.mapToEntity(character)

        assertEquality(characterEntity, character)
    }

    private fun assertEquality(entity: AirportEntity, character: Airport) {
        assertEquals(entity.woeid, character.woeid)
        assertEquals(entity.carriers, character.carriers)
        assertEquals(entity.city, character.city)
        assertEquals(entity.code, character.code)
        assertEquals(entity.country, character.country)
        assertEquals(entity.direct_flights, character.direct_flights)
        assertEquals(entity.elev, character.elev)
        assertEquals(entity.icao, character.icao)
        assertEquals(entity.lat.toDouble(), character.lat)
        assertEquals(entity.name, character.name)
        assertEquals(entity.lon.toDouble(), character.lon)
        assertEquals(entity.phone, character.phone)
        assertEquals(entity.state, character.state)
        assertEquals(entity.runway_length, character.runway_length)
    }

}