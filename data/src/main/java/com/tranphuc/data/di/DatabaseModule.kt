package com.tranphuc.data.di


import android.content.Context
import androidx.room.Room
import com.tranphuc.data.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module


val databaseModule: Module = module {
    single { createAppDatabase(androidContext()) }
}

private fun createAppDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java, "person.db"
    ).build()

}
