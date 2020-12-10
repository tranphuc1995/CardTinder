package com.tranphuc.domain.usecases

import com.tranphuc.domain.model.Person
import com.tranphuc.domain.repository.PersonRepository

class GetListPersonFromRoomUsecase (val personRepository: PersonRepository) {
    suspend fun excute(): List<Person> {
        return personRepository.getListPersonFromRoom()
    }
}