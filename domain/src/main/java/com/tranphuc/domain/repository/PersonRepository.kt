package com.tranphuc.domain.repository

import com.tranphuc.domain.model.Person

interface PersonRepository {
    suspend fun getListPersonFromRemote(): Person
}