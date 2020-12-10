package com.tranphuc.domain.usecases

import com.tranphuc.domain.model.Person
import com.tranphuc.domain.repository.PersonRepository

class GetPersonFromRemoteUsecase(val personRepository: PersonRepository) {
    suspend fun excute(): Person{
        return personRepository.getPersonFromRemote()
    }
}