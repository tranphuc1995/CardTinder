package com.tranphuc.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tranphuc.data.model.PersonEntity

@Dao
interface PersonDAO {
    @Query("SELECT * FROM personentity")
    fun getAllPersonEntity(): List<PersonEntity>

    @Insert
    fun insertPersonEntity(personEntity: PersonEntity)
}