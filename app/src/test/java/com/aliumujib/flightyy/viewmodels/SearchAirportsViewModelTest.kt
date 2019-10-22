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
import java.util.*

@RunWith(JUnit4::class)
class SearchAirportsViewModelTest {

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
        verify(fetchAirports,times(1)).execute(any(), eq(null))
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
    fun `check that calling search in viewModel returns error when query length is less than 3`() {
        val listOfAirports = PresentationDataFactory.makeAirportList(2)

        searchAirportsViewModel.fetchAirports()

        argumentCaptor<DisposableObserver<List<Airport>>>().apply {
            verify(fetchAirports).execute(eq(capture()))
            firstValue.onNext(listOfAirports)
        }

        searchAirportsViewModel.searchOrigin("BB")

        assertEquals(Status.ERROR, searchAirportsViewModel.airportsData.value?.status)
    }

    @Test
    fun `check that calling search in viewModel returns success when airport is found`() {
        val listOfAirports = PresentationDataFactory.makeAirportList(2)

        searchAirportsViewModel.fetchAirports()

        argumentCaptor<DisposableObserver<List<Airport>>>().apply {
            verify(fetchAirports).execute(eq(capture()))
            firstValue.onNext(listOfAirports)
        }

        val filterFor = searchAirportsViewModel.airportList[0]
        searchAirportsViewModel.searchOrigin(filterFor.city)

        assertEquals(Status.SUCCESS, searchAirportsViewModel.airportsData.value?.status)
    }


    @Test
    fun `check that calling search in viewModel returns error when airport is not found`() {
        val listOfAirports = PresentationDataFactory.makeAirportList(2)

        searchAirportsViewModel.fetchAirports()

        argumentCaptor<DisposableObserver<List<Airport>>>().apply {
            verify(fetchAirports).execute(eq(capture()))
            firstValue.onNext(listOfAirports)
        }

        searchAirportsViewModel.searchOrigin(UUID.randomUUID().toString())

        assertEquals(Status.ERROR, searchAirportsViewModel.airportsData.value?.status)
    }


    @Test
    fun `check that fetching airports in viewModel returns error when an error occurs`() {

        searchAirportsViewModel.fetchAirports()

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
