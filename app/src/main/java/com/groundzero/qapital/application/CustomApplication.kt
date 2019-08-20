package com.groundzero.qapital.application

import android.app.Application
import com.groundzero.qapital.di.components.ApplicationComponent
import com.groundzero.qapital.di.components.DaggerApplicationComponent
import com.groundzero.qapital.di.modules.RemoteModule

class CustomApplication : Application() {

    private var applicationComponent: ApplicationComponent? = null

    fun getApplicationComponent(): ApplicationComponent {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                .remoteModule(RemoteModule())
                .build()
        }
        return applicationComponent!!
    }
}