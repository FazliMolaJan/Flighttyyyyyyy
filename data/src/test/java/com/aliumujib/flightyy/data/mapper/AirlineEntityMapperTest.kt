package com.aliumujib.flightyy.data.mapper

import com.aliumujib.flightyy.data.DummyDataFactory
import com.aliumujib.flightyy.data.model.AirlineEntity
import com.aliumujib.flightyy.domain.models.Airline
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class AirlineEntityMapperTest {

    var entityMapper: AirlineEntityMapper = AirlineEntityMapper()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }


    @Test
    fun `check that AirlineEntity mapped to domain Airline contains correct info`() {
        val airlineEntity: AirlineEntity = DummyDataFactory.makeRandomAirlineEntity()
        val airline:Airline = entityMapper.mapFromEntity(airlineEntity)

        assertEqualData(airlineEntity, airline)
    }


    @Test
    fun `check that Airline mapped to data AirlineEntity contains correct info`() {
        val airlineEntity: Airline = DummyDataFactory.makeRandomAirline()
        val airline = entityMapper.mapToEntity(airlineEntity)

        assertEqualData(airline, airlineEntity)
    }

    fun assertEqualData(entity: AirlineEntity, domain: Airline) {
        assertEquals(entity.id, domain.id)
        assertEquals(entity.active, domain.active)
        assertEquals(entity.alias, domain.alias)
        assertEquals(entity.callsign, domain.callsign)
        assertEquals(entity.country, domain.country)
        assertEquals(entity.iata, domain.iata)
        assertEquals(entity.icao, domain.icao)
        assertEquals(entity.name, domain.name)
    }

}