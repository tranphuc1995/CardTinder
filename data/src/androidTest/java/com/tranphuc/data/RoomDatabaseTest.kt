package com.tranphuc.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.tranphuc.data.database.AppDatabase
import com.tranphuc.data.di.BASE_URL
import com.tranphuc.data.mapper.PersonApiToPerson
import com.tranphuc.data.mapper.PersonEntityToPerson
import com.tranphuc.data.remote.api.PersonService
import com.tranphuc.domain.repository.PersonRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RoomDatabaseTest {

    private lateinit var mPersonRepository: PersonRepository
    private lateinit var mPersonService: PersonService
    private lateinit var mAppDatabase: AppDatabase

    @Before
    fun setUp() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
        mPersonService = retrofit.create(PersonService::class.java)

        mAppDatabase = Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java, "person.db"
        ).build()

        mPersonRepository = PersonRepositoryImpl(
            mPersonService,
            PersonApiToPerson(), mAppDatabase, PersonEntityToPerson()
        )
    }

    @Test
    fun test_function_save_person_to_room() = runBlocking {
        mAppDatabase.personDAO().deleteAllRecordPersonEntity()
        mPersonRepository.savePersonToRoom("3", "demo", "phuc", "HCM", "09090909", 790794000)
        assertEquals(1, mAppDatabase.personDAO().getAllPersonEntity().size)
    }

    @Test
    fun test_function_get_all_person_entity() = runBlocking {
        mAppDatabase.personDAO().deleteAllRecordPersonEntity()
        mPersonRepository.savePersonToRoom("1", "demo", "phuc", "HCM", "09090909", 790794000)
        mPersonRepository.savePersonToRoom("2", "demo", "phuc", "HCM", "09090909", 790794000)
        mPersonRepository.savePersonToRoom("3", "demo", "phuc", "HCM", "09090909", 790794000)
        assertEquals(3, mPersonRepository.getListPersonFromRoom().size)
    }
}