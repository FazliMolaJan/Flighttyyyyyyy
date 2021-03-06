package com.aliumujib.flightyy.domain.usecases.flights

import com.aliumujib.flightyy.domain.executor.PostExecutionThread
import com.aliumujib.flightyy.domain.models.schedule.Schedule
import com.aliumujib.flightyy.domain.repositories.schedules.ISchedulesRepository
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
class FetchFlightsForTest {

    private lateinit var getPlanets: FetchFlights
    @Mock
    lateinit var schedulesRepository: ISchedulesRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getPlanets = FetchFlights(schedulesRepository, postExecutionThread)
    }

    @Test
    fun `confirm that calling getFlightSchedules completes`() {
        stubGetSchedule(Single.just(DummyDataFactory.makeFlightSchedule(10)))
        val testObserver = getPlanets.buildUseCaseSingle(
            FetchFlights.Params.make(
                randomBuild(), randomBuild(), randomBuild()
            )
        ).test()

        testObserver.assertComplete()
    }

    @Test
    fun `confirm that calling getFlightSchedules returns data`() {
        val schedules = DummyDataFactory.makeFlightSchedule(10)
        stubGetSchedule(Single.just(schedules))
        val testObserver = getPlanets.buildUseCaseSingle(FetchFlights.Params.make(randomBuild(), randomBuild(), randomBuild())).test()
        testObserver.assertValue(schedules)
    }

    @Test(expected = IllegalStateException::class)
    fun `confirm that using FetchPlanetsWithID without params throws an exception`() {
        val planet = DummyDataFactory.makeFlightSchedule(10)
        stubGetSchedule(Single.just(planet))
        val testObserver = getPlanets.buildUseCaseSingle().test()
        testObserver.assertValue(planet)
    }

    private fun stubGetSchedule(single: Single<List<Schedule>>) {
        whenever(schedulesRepository.getFlightSchedules(any(), any(), any()))
            .thenReturn(single)
    }

}