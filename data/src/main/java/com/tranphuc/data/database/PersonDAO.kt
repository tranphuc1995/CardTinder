package com.tranphuc.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tranphuc.data.model.PersonEntity

@Dao
interface PersonDAO {
    @Query("SELECT * FROM personentity")
    fun getAllPersonEntity(): List<PersonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPersonEntity(personEntity: PersonEntity)

    // use for unit test
    @Query("DELETE FROM personentity")
    fun deleteAllRecordPersonEntity()
}