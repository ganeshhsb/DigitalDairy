package com.ganesh.compose.usecase

import androidx.lifecycle.LiveData
import com.ganesh.compose.data.dao.PeopleDao
import com.ganesh.compose.data.model.People
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
class PeopleUsecase @Inject constructor(val dao: PeopleDao) {
    suspend fun insert(people: People) {
        dao.insert(people)
    }

    fun getAllAsLiveData(): LiveData<List<People>> {
       return dao.getAllAsLiveData()
    }
}