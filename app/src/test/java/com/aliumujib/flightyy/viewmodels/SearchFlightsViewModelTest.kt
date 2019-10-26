package com.aliumujib.flightyy.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aliumujib.flightyy.PresentationDataFactory
import com.aliumujib.flightyy.domain.models.schedule.Schedule
import com.aliumujib.flightyy.domain.usecases.flights.FetchFlights
import com.aliumujib.flightyy.presentation.mappers.*
import com.aliumujib.flightyy.presentation.models.AirportModel
import com.aliumujib.flightyy.presentation.state.Status
import com.aliumujib.flightyy.presentation.viewmodels.SearchFlightsViewModel
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

@RunWith(JUnit4::class)
class SearchFlightsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var fetchFlights: FetchFlights

    private val airportMapper = AirportMapper()
    private val airlineMapper = AirlineMapper()
    private val arrivalDepartureMapper = ArrivalDepartureMapper(airportMapper)
    private val flightMapper = FlightMapper(airlineMapper, arrivalDepartureMapper)
    private val scheduleMapper = ScheduleMapper(flightMapper)

    private lateinit var searchFlightsViewModel: SearchFlightsViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        val listOfSchedules = PresentationDataFactory.makeScheduleList(2)
        whenever(fetchFlights.buildUseCaseSingle(eq(null))).thenReturn(
            Single.just(
                listOfSchedules
            )
        )

        searchFlightsViewModel =
            SearchFlightsViewModel(fetchFlights, airportMapper, scheduleMapper)
    }

    @Test
    fun `check that searchFlights executes FetchFlights UseCase`() {
        val (origin, destination, dateTime) = callSearchMethod()
        verify(fetchFlights).execute(
            any(),
            eq(FetchFlights.Params.make(
                airportMapper.mapToDomain(origin),
                airportMapper.mapToDomain(destination),
                dateTime
            ))
        )
    }

    @Test
    fun `check that executing FetchFlights returns correctly mapped data`() {
        val list = PresentationDataFactory.makeScheduleList(2)
        val mapped = list.map {
            scheduleMapper.mapToView(it)
        }

        val (origin, destination, dateTime) = callSearchMethod()

        argumentCaptor<DisposableSingleObserver<List<Schedule>>>().apply {
            verify(fetchFlights).execute(
                capture(),
                eq(FetchFlights.Params.make(
                    airportMapper.mapToDomain(origin),
                    airportMapper.mapToDomain(destination),
                    dateTime
                ))
            )
            firstValue.onSuccess(list)
        }

        assertEquals(mapped, searchFlightsViewModel.schedules.value?.data)
    }

    @Test
    fun `check that calling searchFlights in viewModel returns success when data is returned`() {
        val listOfSchedules = PresentationDataFactory.makeScheduleList(2)

        val (origin, destination, dateTime) = callSearchMethod()

        argumentCaptor<DisposableSingleObserver<List<Schedule>>>().apply {
            verify(fetchFlights).execute(
                capture(),
                eq(FetchFlights.Params.make(
                    airportMapper.mapToDomain(origin),
                    airportMapper.mapToDomain(destination),
                    dateTime
                ))
            )
            firstValue.onSuccess(listOfSchedules)
        }

        assertEquals(Status.SUCCESS, searchFlightsViewModel.schedules.value?.status)
    }

    private fun callSearchMethod(): Triple<AirportModel, AirportModel, String> {
        val origin = PresentationDataFactory.makeAirportModel()
        val destination = PresentationDataFactory.makeAirportModel()
        val dateTime = UUID.randomUUID().toString()

        searchFlightsViewModel.searchFlights(origin, destination, dateTime)
        return Triple(origin, destination, dateTime)
    }

    @Test
    fun `check that calling searchFlights in viewModel returns error when an error occurs`() {

        val (origin, destination, dateTime) = callSearchMethod()

        argumentCaptor<DisposableSingleObserver<List<Schedule>>>().apply {
            verify(fetchFlights).execute(
                capture(),
                eq(FetchFlights.Params.make(
                    airportMapper.mapToDomain(origin),
                    airportMapper.mapToDomain(destination),
                    dateTime
                ))
            )
            firstValue.onError(IllegalAccessException())
        }

        assertEquals(Status.ERROR, searchFlightsViewModel.schedules.value?.status)
    }
}
