package com.aliumujib.flightyy.ui.inject

import com.aliumujib.flightyy.ui.ApplicationClass
import com.aliumujib.flightyy.ui.inject.module.ApplicationModule
import com.aliumujib.flightyy.ui.inject.module.presentation.PresentationModule
import com.aliumujib.flightyy.ui.inject.module.ui.UIModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class, ApplicationModule::class, UIModule::class,
        PresentationModule::class, RemoteModule::class, DataModule::class, CacheModule::class]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: ApplicationClass): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: ApplicationClass)
}