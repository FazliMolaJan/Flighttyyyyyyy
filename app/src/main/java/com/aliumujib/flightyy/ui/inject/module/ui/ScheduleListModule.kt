package com.aliumujib.flightyy.ui.inject.module.ui

import com.aliumujib.flightyy.presentation.models.schedule.ScheduleModel
import com.aliumujib.flightyy.ui.adapters.base.BindableItemClickListener
import com.aliumujib.flightyy.ui.adapters.schedules.SchedulesListAdapter
import com.aliumujib.flightyy.ui.flightlist.ScheduleListFragment
import dagger.Module
import dagger.Provides

@Module
class ScheduleListModule {

    @Provides
    fun providesAdapter(clickListener: BindableItemClickListener<ScheduleModel>): SchedulesListAdapter {
        return SchedulesListAdapter(clickListener)
    }

    @Provides
    fun providesClickListener(scheduleListFragment:ScheduleListFragment): BindableItemClickListener<ScheduleModel> {
        return scheduleListFragment
    }

}