package com.aliumujib.flightyy.ui.inject.module.ui

import com.aliumujib.flightyy.domain.executor.PostExecutionThread
import com.aliumujib.flightyy.ui.MainActivity
import com.aliumujib.flightyy.ui.auth.LoginFragment
import com.aliumujib.flightyy.ui.filter.FiltersFragment
import com.aliumujib.flightyy.ui.flightlist.ScheduleListFragment
import com.aliumujib.flightyy.ui.search.AirportSearchFragment
import com.aliumujib.flightyy.ui.utils.PostExecutionThreadImpl
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIModule {

    @Binds
    abstract fun bindPostExecutionThread(postExecutionThread: PostExecutionThreadImpl): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributesSearchFragment(): FiltersFragment

    @ContributesAndroidInjector(modules = [ScheduleListModule::class])
    abstract fun contributesScheduleListFragment(): ScheduleListFragment

    @ContributesAndroidInjector(modules = [AirportSearchModule::class])
    abstract fun contributesDetailsFragment(): AirportSearchFragment

    @ContributesAndroidInjector
    abstract fun contributesLoginFragment(): LoginFragment
}