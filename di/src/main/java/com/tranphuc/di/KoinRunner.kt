package com.tranphuc.di

import android.app.Application
import android.content.Context
import com.tranphuc.data.di.DataKoin
import com.tranphuc.home_page.di.HomePageKoin
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module


object KoinRunner {
    @JvmStatic
    fun initDi(application: Application) {
        val androidContextModule = module {
            single<Context> { application }
        }

        val modules = mutableListOf<Module>().apply {
            add(androidContextModule)
            addAll(DataKoin.getModule())
            addAll(HomePageKoin.getModule())
        }

        startKoin {
            modules(modules)
        }
    }
}