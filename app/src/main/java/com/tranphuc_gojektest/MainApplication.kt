package com.tranphuc_gojektest

import android.app.Application
import com.tranphuc.di.KoinRunner

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinRunner.initDi(this)
    }
}