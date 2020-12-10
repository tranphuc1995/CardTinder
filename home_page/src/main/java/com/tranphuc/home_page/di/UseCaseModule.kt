package com.tranphuc.home_page.di
import com.tranphuc.domain.usecases.GetListPersonFromRoomUsecase
import com.tranphuc.domain.usecases.GetPersonFromRemoteUsecase
import com.tranphuc.domain.usecases.SavePersonToRoomUsecase
import org.koin.core.module.Module
import org.koin.dsl.module

val useCaseModule: Module = module {
    factory { GetPersonFromRemoteUsecase(get()) }
    factory { SavePersonToRoomUsecase(get()) }
    factory { GetListPersonFromRoomUsecase(get()) }
}