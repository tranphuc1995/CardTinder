package com.tranphuc.domain.usecases

import com.tranphuc.domain.repository.PersonRepository

class SavePersonToRoomUsecase(private val personRepository: PersonRepository) {
    suspend fun excute(
        id: String,
        avatar: String,
        name: String,
        location: String,
        phone: String,
        dob: Long
    ) {
        personRepository.savePersonToRoom(id, avatar, name, location, phone, dob)
    }
}