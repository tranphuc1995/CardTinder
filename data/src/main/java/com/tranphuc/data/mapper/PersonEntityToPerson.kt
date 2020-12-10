package com.tranphuc.data.mapper

import com.tranphuc.data.model.PersonEntity
import com.tranphuc.domain.model.Person

class PersonEntityToPerson {
    fun map(personEntity: PersonEntity): Person {
        return Person(
            personEntity.id,
            personEntity.name,
            personEntity.dob,
            personEntity.location,
            personEntity.phone,
            personEntity.avatar
        )
    }
}