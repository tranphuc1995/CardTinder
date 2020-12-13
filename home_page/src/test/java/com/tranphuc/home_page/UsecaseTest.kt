package com.tranphuc.home_page

import com.tranphuc.domain.model.Person
import com.tranphuc.domain.repository.PersonRepository
import com.tranphuc.domain.usecases.GetListPersonFromRoomUsecase
import com.tranphuc.domain.usecases.GetPersonFromRemoteUsecase
import com.tranphuc.domain.usecases.SavePersonToRoomUsecase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class UsecaseTest {
    private val personRepository: PersonRepository = mock(PersonRepository::class.java)

    @Test
    fun test_usecase_get_person_from_remote() = runBlocking {
        val getPersonFromRemoteUsecase = GetPersonFromRemoteUsecase(personRepository)
        val person = Person("1", "demo", 790794000, "HCM", "09090909", "")
        Mockito.`when`(personRepository.getPersonFromRemote())
            .thenReturn(person)

        Assert.assertEquals(person, getPersonFromRemoteUsecase.excute())
    }

    @Test
    fun test_usecase_get_list_person_from_room_in_case_list_greater_or_equal_2() = runBlocking {
        val getListPersonFromRoomUsecase = GetListPersonFromRoomUsecase(personRepository)
        val listPerson: MutableList<Person> = ArrayList()
        listPerson.add(Person("1", "demo", 790794000, "HCM", "09090909", ""))
        listPerson.add(Person("2", "demo", 790794000, "HCM", "09090909", ""))
        listPerson.add(Person("3", "demo", 790794000, "HCM", "09090909", ""))
        Mockito.`when`(personRepository.getListPersonFromRoom())
            .thenReturn(listPerson)

        val listPersonResult: MutableList<Person> = ArrayList()
        listPersonResult.add(Person("3", "demo", 790794000, "HCM", "09090909", ""))
        listPersonResult.add(Person("1", "demo", 790794000, "HCM", "09090909", ""))
        listPersonResult.add(Person("2", "demo", 790794000, "HCM", "09090909", ""))
        listPersonResult.add(Person("3", "demo", 790794000, "HCM", "09090909", ""))
        listPersonResult.add(Person("1", "demo", 790794000, "HCM", "09090909", ""))
        Assert.assertEquals(listPersonResult, getListPersonFromRoomUsecase.excute())
    }

    @Test
    fun test_usecase_get_list_person_from_room_in_case_less_than_2() = runBlocking {
        val getListPersonFromRoomUsecase = GetListPersonFromRoomUsecase(personRepository)
        val listPerson: MutableList<Person> = ArrayList()
        listPerson.add(Person("1", "demo", 790794000, "HCM", "09090909", ""))
        Mockito.`when`(personRepository.getListPersonFromRoom())
            .thenReturn(listPerson)
        Assert.assertEquals(listPerson, getListPersonFromRoomUsecase.excute())
    }

    @Test
    fun test_usecase_save_person_to_room() = runBlocking {
        val savePersonToRoomUsecase = SavePersonToRoomUsecase(personRepository)
        savePersonToRoomUsecase.excute("1","","phuc","HCM","09090909",790794000)
    }
}