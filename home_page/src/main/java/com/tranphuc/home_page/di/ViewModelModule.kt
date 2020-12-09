package com.tranphuc.home_page.di

import com.tranphuc.home_page.viewmodel.HomePageViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewmodelModule: Module = module {
    viewModel { HomePageViewModel(get(), get()) }
}