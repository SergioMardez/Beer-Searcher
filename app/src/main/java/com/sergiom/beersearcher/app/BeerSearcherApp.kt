package com.sergiom.beersearcher.app

import android.app.Application
import android.content.Context
import androidx.viewbinding.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BeerSearcherApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        instance = this
    }

    companion object {
        lateinit var instance: BeerSearcherApp

        fun getAppContext(): Context = instance.applicationContext
    }

}