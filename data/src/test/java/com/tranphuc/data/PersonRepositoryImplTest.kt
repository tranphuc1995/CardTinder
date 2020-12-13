package com.tranphuc.data

import android.content.Context
import androidx.room.Room
import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.tranphuc.data.database.AppDatabase
import com.tranphuc.data.di.BASE_URL
import com.tranphuc.data.mapper.PersonApiToPerson
import com.tranphuc.data.mapper.PersonEntityToPerson
import com.tranphuc.data.remote.api.PersonService
import com.tranphuc.domain.model.Person
import com.tranphuc.domain.repository.PersonRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


@RunWith(MockitoJUnitRunner::class)
class PersonRepositoryImplTest {
    private lateinit var mPersonRepository: PersonRepository
    private lateinit var mPersonService: PersonService
    private lateinit var mAppDatabase: AppDatabase

    @Before
    fun setUp() {
        // init retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
        mPersonService = retrofit.create(PersonService::class.java)

        // init app database
        mAppDatabase = Room.databaseBuilder(
            mock(Context::class.java),
            AppDatabase::class.java, "person.db"
        ).build()

        val personApiToPerson = PersonApiToPerson()
        val presonEntityToPerson = PersonEntityToPerson()
        mPersonRepository = PersonRepositoryImpl(
            mPersonService,
            personApiToPerson,
            mAppDatabase,
            presonEntityToPerson
        )
    }

    @Test
    fun test_function_get_person_from_remote_in_case_success() = runBlocking {
        var person = Person()
        person = mPersonRepository.getPersonFromRemote()
        Assert.assertNotEquals("", person.id)
    }

    @Test
    fun test_function_get_person_from_remote_in_case_network_error() = runBlocking {
        val personService = mock(PersonService::class.java)
        val personRepository = PersonRepositoryImpl(
            personService,
            PersonApiToPerson(),
            mock(AppDatabase::class.java),
            PersonEntityToPerson()
        )
        Mockito.`when`(personService.getPerson())
            .thenReturn(NetworkResponse.NetworkError(IOException()))
        Assert.assertEquals("", personRepository.getPersonFromRemote().id)
    }

    @Test
    fun test_function_get_person_from_remote_in_case_server_error() = runBlocking {
        val personService = mock(PersonService::class.java)
        val personRepository = PersonRepositoryImpl(
            personService,
            PersonApiToPerson(),
            mock(AppDatabase::class.java),
            PersonEntityToPerson()
        )
        Mockito.`when`(personService.getPerson())
            .thenReturn(NetworkResponse.ServerError(Unit, 400))
        Assert.assertEquals("", personRepository.getPersonFromRemote().id)
    }

    @Test
    fun test_function_get_person_from_remote_in_case_unknown_error() = runBlocking {
        val personService = mock(PersonService::class.java)
        val personRepository = PersonRepositoryImpl(
            personService,
            PersonApiToPerson(),
            mock(AppDatabase::class.java),
            PersonEntityToPerson()
        )
        Mockito.`when`(personService.getPerson())
            .thenReturn(NetworkResponse.UnknownError(Throwable("error")))
        Assert.assertEquals("", personRepository.getPersonFromRemote().id)
    }
}