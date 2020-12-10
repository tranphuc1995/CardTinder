package com.tranphuc.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tranphuc.data.model.PersonEntity

@Database(
    entities = [PersonEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDAO() : PersonDAO
}