package com.tranphuc.data.di


import com.tranphuc.data.PersonRepositoryImpl
import com.tranphuc.domain.repository.PersonRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    factory { PersonRepositoryImpl(get(), get()) as PersonRepository }
}