package com.aliumujib.flightyy.ui.inject.module.ui

import com.aliumujib.flightyy.R
import com.aliumujib.flightyy.presentation.models.AirportModel
import com.aliumujib.flightyy.ui.adapters.base.BindableItemClickListener
import com.aliumujib.flightyy.ui.adapters.base.SingleLayoutAdapter
import com.aliumujib.flightyy.ui.search.AirportSearchFragment
import dagger.Module
import dagger.Provides

@Module
class AirportSearchModule {

    @Provides
    fun providesAdapter(bindableItemClickListener: BindableItemClickListener<AirportModel>): SingleLayoutAdapter<AirportModel> {
        return SingleLayoutAdapter(R.layout.item_airport, bindableItemClickListener)
    }

    @Provides
    fun providesClickListener(airportSearchFragment: AirportSearchFragment): BindableItemClickListener<AirportModel> {
        return airportSearchFragment
    }
}