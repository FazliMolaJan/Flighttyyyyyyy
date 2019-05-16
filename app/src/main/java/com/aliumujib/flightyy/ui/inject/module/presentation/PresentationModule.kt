package com.aliumujib.flightyy.ui.inject.module.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliumujib.flightyy.presentation.viewmodels.FlightFiltersViewModel
import com.aliumujib.flightyy.presentation.viewmodels.SearchAirportsViewModel
import com.aliumujib.flightyy.presentation.viewmodels.SearchFlightsViewModel
import com.aliumujib.flightyy.ui.inject.ViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass


@Module
abstract class PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchAirportsViewModel::class)
    abstract fun bindSearchAirportsViewModel(viewModel: SearchAirportsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FlightFiltersViewModel::class)
    abstract fun bindFlightFiltersViewModel(
        viewModel: FlightFiltersViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SearchFlightsViewModel::class)
    abstract fun bindSearchFlightsViewModel(
        viewModel: SearchFlightsViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}



@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)