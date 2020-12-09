package com.tranphuc.home_page.di


object HomePageKoin {
    fun getModule() = listOf(
        useCaseModule,
        mapperModule,
        viewmodelModule
    )
}