package com.aliumujib.flightyy.domain.usecases.airports

import com.aliumujib.flightyy.domain.executor.PostExecutionThread
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.domain.repositories.airports.IAirportsRepository
import com.aliumujib.flightyy.domain.test.DummyDataFactory
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchAirportsTest {

    private lateinit var fetchAirports: FetchAirports
    @Mock
    lateinit var airportsRepository: IAirportsRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        fetchAirports = FetchAirports(airportsRepository, postExecutionThread)
    }

    @Test
    fun `confirm that calling searchAirports completes`() {
        stubSearchAirports(Single.just(DummyDataFactory.makeAirportList(2)))
        val testObserver = fetchAirports.buildUseCaseSingle().test()
        testObserver.assertComplete()
    }

    @Test
    fun `confirm that calling searchAirports returns data`() {
        val list = DummyDataFactory.makeAirportList(10)
        stubSearchAirports(Single.just(list))
        val testObserver = fetchAirports.buildUseCaseSingle().test()
        testObserver.assertValue(list)
    }


    private fun stubSearchAirports(single: Single<List<Airport>>) {
        whenever(airportsRepository.searchAirports())
            .thenReturn(single)
    }

}