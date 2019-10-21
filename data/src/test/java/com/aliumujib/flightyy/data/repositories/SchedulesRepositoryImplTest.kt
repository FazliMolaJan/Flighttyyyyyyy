package com.aliumujib.flightyy.data.repositories

import com.aliumujib.flightyy.data.DummyDataFactory
import com.aliumujib.flightyy.data.cache.airlines.AirlineCache
import com.aliumujib.flightyy.data.cache.airports.AirportCache
import com.aliumujib.flightyy.data.contracts.remote.ISchedulesRemote
import com.aliumujib.flightyy.data.mapper.*
import com.aliumujib.flightyy.data.model.AirportEntity
import com.aliumujib.flightyy.data.model.schedule.ScheduleEntity
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.domain.models.schedule.Schedule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

class SchedulesRepositoryImplTest {

    lateinit var schedulesRepositoryImpl: SchedulesRepositoryImpl

    private val schedulesRemote: ISchedulesRemote = mock()

    private lateinit var scheduleEntityMapper: ScheduleEntityMapper

    private lateinit var flightEntityMapper: FlightEntityMapper

    private val airportEntityMapper = AirportEntityMapper()

    private val airlineEntityMapper = AirlineEntityMapper()

    private lateinit var arrivalDepartureEntityMapper: ArrivalDepartureEntityMapper


    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)

        arrivalDepartureEntityMapper = ArrivalDepartureEntityMapper(airportEntityMapper)
        flightEntityMapper = FlightEntityMapper(airlineEntityMapper, arrivalDepartureEntityMapper)
        scheduleEntityMapper = ScheduleEntityMapper(flightEntityMapper)

        schedulesRepositoryImpl =
            SchedulesRepositoryImpl(
                schedulesRemote,
                scheduleEntityMapper
            )

    }

    @Test
    fun `check that calling getFlightSchedules on repository returns data`() {
        val origin = DummyDataFactory.makeRandomAirport()
        val destination = DummyDataFactory.makeRandomAirport()
        val datetime = UUID.randomUUID().toString()
        val dummyDataEntities = DummyDataFactory.makeScheduleEntities(10)
        val dummyData = scheduleEntityMapper.mapFromEntityList(dummyDataEntities)

        stubSchedulesRemoteResponse(origin.code, destination.code, datetime, dummyDataEntities)

        val testObserver =
            schedulesRepositoryImpl.getFlightSchedules(origin, destination, datetime).test()

        testObserver.assertValue(dummyData)
    }

    @Test
    fun `check that calling getFlightSchedules on repository calls remote implementation`() {
        val origin = DummyDataFactory.makeRandomAirport()
        val destination = DummyDataFactory.makeRandomAirport()
        val datetime = UUID.randomUUID().toString()

        stubSchedulesRemoteResponse(origin.code, destination.code, datetime)
       stubSchedulesRepositoryResponse(origin, destination, datetime)

        schedulesRepositoryImpl.getFlightSchedules(origin, destination, datetime).test()
        verify(schedulesRemote).getSchedules(origin.code, destination.code, datetime)
    }

    private fun stubSchedulesRepositoryResponse(
        origin: Airport,
        destination: Airport,
        datetime: String,
        list: List<Schedule> = DummyDataFactory.makeSchedules(10)
    ) {
        whenever(schedulesRepositoryImpl.getFlightSchedules(origin, destination, datetime))
            .thenReturn(Observable.just(list))
    }

    private fun stubSchedulesRemoteResponse(
        origin: String,
        destination: String,
        datetime: String,
        list: List<ScheduleEntity> = DummyDataFactory.makeScheduleEntities(10)
    ) {
        whenever(schedulesRemote.getSchedules(origin, destination, datetime))
            .thenReturn(Observable.just(list))
    }

}