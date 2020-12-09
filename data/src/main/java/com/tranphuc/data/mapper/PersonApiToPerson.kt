package com.tranphuc.data.mapper
import com.tranphuc.data.model.PersonApi
import com.tranphuc.domain.model.Person

class PersonApiToPerson {
    fun map(personApi: PersonApi?): Person {
        return Person(
            personApi?.md5 ?: "",
            personApi?.name?.first + " " + personApi?.name?.last,
            personApi?.dob ?: 0L,
            personApi?.location?.street + ", " + personApi?.location?.city,
            personApi?.phone ?: "",
            personApi?.picture ?: ""
        )
    }
}