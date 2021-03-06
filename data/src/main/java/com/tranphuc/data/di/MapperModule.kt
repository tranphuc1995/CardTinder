package com.tranphuc.data.di


import com.tranphuc.data.mapper.PersonApiToPerson
import com.tranphuc.data.mapper.PersonEntityToPerson
import org.koin.core.module.Module
import org.koin.dsl.module

val mapperModule: Module = module {
    factory { PersonApiToPerson() }
    factory { PersonEntityToPerson() }
}