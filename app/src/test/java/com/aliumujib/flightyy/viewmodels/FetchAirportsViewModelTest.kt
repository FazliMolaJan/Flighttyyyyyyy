package com.aliumujib.flightyy.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aliumujib.flightyy.PresentationDataFactory
import com.aliumujib.flightyy.domain.interactors.airports.FetchAirports
import com.aliumujib.flightyy.data.model.AirportEntity
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.presentation.mappers.AirportMapper
import com.aliumujib.flightyy.presentation.models.AirportModel
import com.aliumujib.flightyy.presentation.state.Status
import com.aliumujib.flightyy.presentation.viewmodels.SearchAirportsViewModel
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableObserver
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import java.lang.RuntimeException

@RunWith(JUnit4::class)
class FetchAirportsViewModelTest {

    @get:Rule
    val instantExecuterRule = InstantTaskExecutorRule()

    var fetchAirports = mock<FetchAirports>()

    var modelMapper = AirportMapper()

    var searchAirportsViewModel: SearchAirportsViewModel = SearchAirportsViewModel(fetchAirports, modelMapper)

    @Captor
    val captor = argumentCaptor<DisposableObserver<List<Airport>>>()


    @Test
    fun `check that calling searchDestination in viewModel executes FetchAirports when character size is more than 2`() {
        searchAirportsViewModel.searchDestination("luke")
        verify(fetchAirports, times(1)).execute(any())
    }


    @Test
    fun `check that calling searchOrigin in viewModel filters search list when character size is more than 2`() {
        searchAirportsViewModel.searchOrigin("luke")
        verify(fetchAirports, times(1)).execute(any())
    }

    @Test
    fun `check that calling search in viewModel filters search list when character size is less than 3`() {
        searchAirportsViewModel.searchDestination("lu")
        verifyZeroInteractions(fetchAirports)
    }


    @Test
    fun `check that calling search in viewModel returns success when data is returned`() {
        val listOfAirports = PresentationDataFactory.makeAirportList(2)
        val listOfMappedAirports = PresentationDataFactory.makeAirportModelList(2)

        listOfAirports.forEachIndexed { index, airport ->
            stubAirports(airport, listOfMappedAirports[index])
        }

        searchAirportsViewModel.searchOrigin("luke")
        verify(fetchAirports).execute(captor.capture())

        captor.firstValue.onNext(listOfAirports)

        assertEquals(Status.SUCCESS, searchAirportsViewModel.airportsData.value?.status)
    }


    @Test
    fun `check that calling search in viewModel returns correct mapped data`() {
        val listOfCharacters = PresentationDataFactory.makeAirportList(2)
        val listOfMappedCharacters = PresentationDataFactory.makeAirportModelList(2)

        listOfCharacters.forEachIndexed { index, starWarsCharacter ->
            stubAirports(starWarsCharacter, listOfMappedCharacters[index])
        }

        searchAirportsViewModel.searchOrigin("NFC")
        verify(fetchAirports).execute(captor.capture())

        captor.firstValue.onNext(listOfCharacters)

        assertEquals(listOfMappedCharacters, searchAirportsViewModel.airportsData.value?.data)
    }

    @Test
    fun `check that calling search in viewModel returns error when an error occurs`() {

        searchAirportsViewModel.searchDestination("JFL")
        verify(fetchAirports).execute(captor.capture())

        captor.firstValue.onError(RuntimeException())

        assertEquals(Status.ERROR, searchAirportsViewModel.airportsData.value?.status)
    }

    fun stubAirports(character: Airport, characterModel: AirportModel){
        whenever(modelMapper.mapToView(character)).thenReturn(characterModel)
    }


}
