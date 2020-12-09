package com.tranphuc.data.di

object DataKoin {
    fun getModule() = listOf(
        repositoryModule,
        mapperModule,
        networkModule
    )
}