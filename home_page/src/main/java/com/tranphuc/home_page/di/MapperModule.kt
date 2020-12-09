package com.tranphuc.home_page.di


import com.tranphuc.home_page.mapper.PersonToItemPerson
import org.koin.core.module.Module
import org.koin.dsl.module

val mapperModule: Module = module {
    factory { PersonToItemPerson() }

}