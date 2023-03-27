package com.zup.zupbank

import android.app.Application
import com.zup.zupbank.di.daoModule
import com.zup.zupbank.di.repositoryModule
import com.zup.zupbank.di.useCaseModule
import com.zup.zupbank.di.viewModelModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ApplicationApp : Application() {

    override fun onCreate() {
        super.onCreate()

        this.initializeModules()
    }

    private fun initializeModules() {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@ApplicationApp)
            modules(listOf(daoModule, repositoryModule, useCaseModule, viewModelModule))
        }
    }
}
