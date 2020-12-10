package com.tranphuc.domain.repository

import com.tranphuc.domain.model.Person

interface PersonRepository {
    suspend fun getPersonFromRemote(): Person

    suspend fun savePersonToRealm(
        id: String,
        avatar: String,
        name: String,
        location: String,
        phone: String,
        dob: Long
    )

    suspend fun getListPersonFromRoom() : List<Person>
}