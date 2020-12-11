package com.tranphuc.domain.usecases

import com.tranphuc.domain.model.Person
import com.tranphuc.domain.repository.PersonRepository

class GetListPersonFromRoomUsecase(private val personRepository: PersonRepository) {
    suspend fun excute(): List<Person> {
        val listPerson = personRepository.getListPersonFromRoom() as MutableList<Person>
        createCircularListPerson(listPerson)
        return listPerson
    }

    private fun createCircularListPerson(listPerson: MutableList<Person>) {
        if (listPerson.size >= 2) {
            // create circular list person
            listPerson.add(listPerson[0])
            listPerson.add(0, listPerson[listPerson.size - 2])
        }
    }
}