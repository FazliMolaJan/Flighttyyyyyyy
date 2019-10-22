package com.aliumujib.flightyy.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aliumujib.flightyy.PresentationDataFactory
import com.aliumujib.flightyy.domain.usecases.airports.FetchAirports
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.presentation.mappers.AirportMapper
import com.aliumujib.flightyy.presentation.models.AirportModel
import com.aliumujib.flightyy.presentation.state.Status
import com.aliumujib.flightyy.presentation.viewmodels.SearchAirportsViewModel
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.*
import java.lang.RuntimeException

@RunWith(JUnit4::class)
class SearchFlightsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var fetchAirports: FetchAirports

    private val modelMapper = AirportMapper()

    private lateinit var searchAirportsViewModel: SearchAirportsViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)



        val listOfAirports = PresentationDataFactory.makeAirportList(2)
        whenever(fetchAirports.buildUseCaseObservable(eq(null))).thenReturn(Observable.just(listOfAirports))

        searchAirportsViewModel =
            SearchAirportsViewModel(fetchAirports, modelMapper)
    }


    @Test
    fun `check that fetchAirports executes FetchAirports UseCase`() {
        searchAirportsViewModel.fetchAirports()
        verify(fetchAirports).execute(any(), eq(null))
    }

    @Test
    fun `check that executing FetchAirports returns correctly mapped data`() {
        val listOfAirports = PresentationDataFactory.makeAirportList(2)
        val mappedAirports = listOfAirports.map {
            modelMapper.mapToView(it)
        }


        searchAirportsViewModel.fetchAirports()

        argumentCaptor<DisposableObserver<List<Airport>>>().apply {
            verify(fetchAirports).execute(eq(capture()))
            firstValue.onNext(listOfAirports)
        }

        assertEquals(mappedAirports, searchAirportsViewModel.airportList)
    }

    @Test
    fun `check that calling search in viewModel returns success when data is returned`() {
        val listOfAirports = PresentationDataFactory.makeAirportList(2)

        searchAirportsViewModel.searchOrigin("luke")

        argumentCaptor<DisposableObserver<List<Airport>>>().apply {
            verify(fetchAirports).execute(capture())
            firstValue.onNext(listOfAirports)
        }

        assertEquals(Status.SUCCESS, searchAirportsViewModel.airportsData.value?.status)
    }


    @Test
    fun `check that calling search in viewModel returns correct mapped data`() {
        val listOfCharacters = PresentationDataFactory.makeAirportList(2)
        val listOfMappedCharacters = listOfCharacters.map {
            modelMapper.mapToView(it)
        }

        searchAirportsViewModel.searchOrigin("NFC")

        argumentCaptor<DisposableObserver<List<Airport>>>().apply {
            verify(fetchAirports).execute(eq(capture()))
            firstValue.onNext(listOfCharacters)
        }

        assertEquals(listOfMappedCharacters, searchAirportsViewModel.airportsData.value?.data)
    }

    @Test
    fun `check that calling search in viewModel returns error when an error occurs`() {

        searchAirportsViewModel.searchDestination("JFL")

        argumentCaptor<DisposableObserver<List<Airport>>>().apply {
            verify(fetchAirports).execute(eq(capture()))
            firstValue.onError(RuntimeException())
        }

        assertEquals(Status.ERROR, searchAirportsViewModel.airportsData.value?.status)
    }

    private fun stubAirports(airport: Airport, airportModel: AirportModel) {
        whenever(modelMapper.mapToView(airport)).thenReturn(airportModel)
    }


}
