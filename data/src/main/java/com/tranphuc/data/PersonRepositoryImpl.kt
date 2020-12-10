package com.tranphuc.data

import com.haroldadmin.cnradapter.NetworkResponse
import com.tranphuc.data.database.AppDatabase
import com.tranphuc.data.mapper.PersonApiToPerson
import com.tranphuc.data.mapper.PersonEntityToPerson
import com.tranphuc.data.model.PersonEntity
import com.tranphuc.data.remote.api.PersonService
import com.tranphuc.domain.model.Person
import com.tranphuc.domain.repository.PersonRepository


class PersonRepositoryImpl(
    val personService: PersonService,
    val personApiToPerson: PersonApiToPerson,
    val appDatabase: AppDatabase,
    val personEntityToPerson: PersonEntityToPerson
) :
    PersonRepository {
    override suspend fun getPersonFromRemote(): Person {
        /*  for simplicity, if request is in case "ServerError" , "NetworkError" , "UnknownError"
        i will return an obeject with default constructor */
        var person = Person()
        var response = personService.getPerson()
        when (response) {
            is NetworkResponse.Success -> {
                if (response.body.listResult?.size ?: 0 > 0) {
                    var personApi = response.body.listResult?.get(0)?.personApi
                    person = personApiToPerson.map(personApi)
                }
            }
            is NetworkResponse.ServerError -> {

            }
            is NetworkResponse.NetworkError -> {

            }
            is NetworkResponse.UnknownError -> {

            }
        }
        return person
    }

    override suspend fun savePersonToRealm(
        id: String,
        avatar: String,
        name: String,
        location: String,
        phone: String,
        dob: Long
    ) {
        appDatabase.personDAO().insertPersonEntity(
            PersonEntity(
                id, avatar, name, location, phone, dob
            )
        )
    }

    override suspend fun getListPersonFromRoom(): List<Person> {
        var listPersonEntity = appDatabase.personDAO().getAllPersonEntity()
        var listPerson = listPersonEntity.map { personEntity -> personEntityToPerson.map(personEntity) }
        return listPerson
    }
}