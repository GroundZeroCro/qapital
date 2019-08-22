package com.groundzero.qapital.di.components

import com.groundzero.qapital.base.BaseFragment
import com.groundzero.qapital.di.modules.ApplicationModule
import com.groundzero.qapital.di.modules.RemoteModule
import com.groundzero.qapital.ui.details.DetailsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RemoteModule::class])
interface ApplicationComponent {
    fun inject(baseFragment: BaseFragment)
}