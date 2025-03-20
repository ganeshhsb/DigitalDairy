package com.digitaldairy.labour.usecase

import com.digitaldairy.labour.data.dao.PersonDao
import com.digitaldairy.labour.data.model.Person
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//@Singleton
class PersonUsecase @Inject constructor(val dao: PersonDao) {
    suspend fun insert(person: Person) {
        dao.insert(person)
    }

    suspend fun update(person: Person) {
        dao.update(person)
    }

    fun getAllAsLiveData(): Flow<List<Person>> {
       return dao.getAllAsLiveData()
    }
}