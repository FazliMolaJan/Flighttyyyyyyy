package com.aliumujib.flightyy.domain.usecases.airlines

import com.aliumujib.flightyy.domain.executor.PostExecutionThread
import com.aliumujib.flightyy.domain.models.Airline
import com.aliumujib.flightyy.domain.repositories.airlines.IAirlinesRepository
import com.aliumujib.flightyy.domain.test.DummyDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
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
        stubGetAirline(Single.just(DummyDataFactory.makeAirline()))
        val testObserver = getAirlines.buildUseCaseSingle(
            FetchAirlinesForID.Params.make(randomBuild())
        ).test()
        testObserver.assertComplete()
    }

    @Test
    fun `confirm that calling getAirlineWithId returns data`() {
        val list = DummyDataFactory.makeAirline()
        stubGetAirline(Single.just(list))
        val testObserver = getAirlines.buildUseCaseSingle(
            FetchAirlinesForID.Params.make(randomBuild())
        ).test()
        testObserver.assertValue(list)
    }

    @Test(expected = IllegalStateException::class)
    fun `confirm that using FetchAirlinesForID without params throws an exception`() {
        val projects = DummyDataFactory.makeAirline()
        stubGetAirline(Single.just(projects))
        val testObserver = getAirlines.buildUseCaseSingle().test()
        testObserver.assertValue(projects)
    }

    private fun stubGetAirline(observable: Single<Airline>) {
        whenever(airlinesRepository.getAirlineWithId(any()))
            .thenReturn(observable)
    }

}