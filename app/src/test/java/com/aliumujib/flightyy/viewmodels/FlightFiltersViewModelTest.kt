package com.aliumujib.flightyy.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aliumujib.flightyy.PresentationDataFactory
import com.aliumujib.flightyy.domain.interactors.airports.FetchCharacterDetails
import com.aliumujib.flightyy.data.model.schedule.ScheduleEntity
import com.aliumujib.flightyy.presentation.state.Status
import com.aliumujib.flightyy.presentation.viewmodels.FlightFiltersViewModel
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
class FlightFiltersViewModelTest {

    @get:Rule
    val instantExecuterRule = InstantTaskExecutorRule()

    var fetchCharacterDetails = mock<FetchCharacterDetails>()

    var detailmodelMapper = mock<DetailedCharacterModelMapper>()

    var characterModelMapper = StarwarsCharacterModelMapper()



    var projectsViewModel: FlightFiltersViewModel =
        FlightFiltersViewModel(fetchCharacterDetails, characterModelMapper, detailmodelMapper)

    @Captor
    val captor = argumentCaptor<DisposableObserver<ScheduleEntity>>()


    @Test
    fun `check that calling fetchCharacterDetails in viewModel executes FetchCharacterDetails`() {
        val data = PresentationDataFactory.makeAirportModel()
        projectsViewModel.fetchCharacterDetails(data)
        verify(fetchCharacterDetails, times(1)).execute(any(), any())
    }


    @Test
    fun `check that calling getCharacterDetailsLiveData on viewModel returns success when data is returned`() {
        val characterData = PresentationDataFactory.makeAirportModel()
        val data = PresentationDataFactory.makeDetailedCharacter()
        val detailsData = PresentationDataFactory.makeDetailedCharacterModel()

        stubCharacterDetails(data, detailsData)

        projectsViewModel.fetchCharacterDetails(characterData)
        verify(fetchCharacterDetails).execute(captor.capture(), eq(FetchCharacterDetails.Params(characterModelMapper.mapToDomain(characterData))))

        captor.firstValue.onNext(data)

        assertEquals(Status.SUCCESS, projectsViewModel.getCharacterDetailsLiveData().value?.status)
    }


    @Test
    fun `check that getCharacterDetailsLiveData in viewModel returns correct mapped data`() {
        val characterData = PresentationDataFactory.makeAirportModel()
        val data = PresentationDataFactory.makeDetailedCharacter()
        val detailsData = PresentationDataFactory.makeDetailedCharacterModel()

        stubCharacterDetails(data, detailsData)

        projectsViewModel.fetchCharacterDetails(characterData)
        verify(fetchCharacterDetails).execute(captor.capture(), eq(FetchCharacterDetails.Params(characterModelMapper.mapToDomain(characterData))))

        captor.firstValue.onNext(data)

        assertEquals(detailsData, projectsViewModel.getCharacterDetailsLiveData().value?.data)
    }

    @Test
    fun `check that getCharacterDetailsLiveData in viewModel returns error when an error occurs`() {
        val characterData = PresentationDataFactory.makeAirportModel()
        projectsViewModel.fetchCharacterDetails(characterData)

        verify(fetchCharacterDetails).execute(captor.capture(), eq(FetchCharacterDetails.Params(characterModelMapper.mapToDomain(characterData))))

        captor.firstValue.onError(RuntimeException())

        assertEquals(Status.ERROR, projectsViewModel.getCharacterDetailsLiveData().value?.status)
    }

    fun stubCharacterDetails(character: ScheduleEntity, characterModel: DetailedCharacterModel) {
        whenever(detailmodelMapper.mapToView(character)).thenReturn(characterModel)
    }


}
