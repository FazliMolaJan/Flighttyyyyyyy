package com.aliumujib.flightyy.ui.inject.module.ui

import com.aliumujib.flightyy.ui.adapters.schedules.SchedulesListAdapter
import dagger.Module
import dagger.Provides

@Module
class ScheduleListModule {

    @Provides
    fun providesAdapter(): SchedulesListAdapter {
        return SchedulesListAdapter()
    }


}