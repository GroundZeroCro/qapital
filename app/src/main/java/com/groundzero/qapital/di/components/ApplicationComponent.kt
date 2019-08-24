package com.groundzero.qapital.di.components

import com.groundzero.qapital.base.BaseFragment
import com.groundzero.qapital.di.modules.ApplicationModule
import com.groundzero.qapital.di.modules.PersistenceModule
import com.groundzero.qapital.di.modules.RemoteModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RemoteModule::class, PersistenceModule::class])
interface ApplicationComponent {
    fun inject(baseFragment: BaseFragment)
}