package com.aliumujib.flightyy.domain.interactors.airlines

import com.aliumujib.flightyy.domain.executor.PostExecutionThread
import com.aliumujib.flightyy.domain.models.Airline
import com.aliumujib.flightyy.domain.repositories.airlines.IAirlinesRepository
import com.aliumujib.flightyy.domain.test.DummyDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import konveyor.base.randomBuild
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchAirlinesForIDTest {

    @Mock
    lateinit var airlinesRepository: IAirlinesRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    private lateinit var getAirlines: FetchAirlinesForID

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getAirlines = FetchAirlinesForID(airlinesRepository, postExecutionThread)
    }

    @Test
    fun `confirm that calling getAirlineWithId completes`() {
        stubGetAirline(Observable.just(DummyDataFactory.makeAirline()))
        val testObserver = getAirlines.buildUseCaseObservable(
            FetchAirlinesForID.Params.make(randomBuild())
        ).test()
        testObserver.assertComplete()
    }

    @Test
    fun `confirm that calling getAirlineWithId returns data`() {
        val list = DummyDataFactory.makeAirline()
        stubGetAirline(Observable.just(list))
        val testObserver = getAirlines.buildUseCaseObservable(
            FetchAirlinesForID.Params.make(randomBuild())
        ).test()
        testObserver.assertValue(list)
    }

    @Test(expected = IllegalStateException::class)
    fun `confirm that using FetchAirlinesForID without params throws an exception`() {
        val projects = DummyDataFactory.makeAirline()
        stubGetAirline(Observable.just(projects))
        val testObserver = getAirlines.buildUseCaseObservable().test()
        testObserver.assertValue(projects)
    }

    private fun stubGetAirline(observable: Observable<Airline>) {
        whenever(airlinesRepository.getAirlineWithId(any()))
            .thenReturn(observable)
    }

}