package com.aliumujib.flightyy.ui.inject.module

import android.content.Context
import com.aliumujib.flightyy.ui.ApplicationClass
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {


    @Binds
    abstract fun bindsContext(applicationClass: ApplicationClass): Context



}