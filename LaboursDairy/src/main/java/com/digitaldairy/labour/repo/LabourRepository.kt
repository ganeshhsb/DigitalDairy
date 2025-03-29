package com.digitaldairy.labour.repo

import com.digitaldairy.labour.FirebaseDataStore
import com.digitaldairy.labour.RoomDataStore
import com.digitaldairy.labour.data.model.DailyWork
import com.digitaldairy.labour.data.model.Person
import com.digitaldairy.labour.data.model.PersonWithWorkDetail
import com.digitaldairy.labour.data.model.WorkDetail
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

interface ILabourRepository {
    fun getAllPersonsDataAsFlow(): Flow<List<Person>>
    suspend fun insertPerson(person: Person)
    suspend fun insertWorkDetail(personId: String, workDetail: DailyWork)
    fun getAllWorkDetailsFor(uId: String): Flow<PersonWithWorkDetail>
    fun getWorkInfo(uId: String, date: Date): PersonWithWorkDetail
}

@Singleton
class LabourRepository @Inject constructor(
    private val roomDataStore: RoomDataStore,
    private val firebaseDataStore: FirebaseDataStore
) : ILabourRepository {
    override fun getAllPersonsDataAsFlow(): Flow<List<Person>> {
        return roomDataStore.getAllPersonsAsWorkflow()
    }

    override suspend fun insertPerson(person: Person) {
        roomDataStore.insertPerson(person)
//        firebaseDataStore.insertPeople(people)
    }

    override suspend fun insertWorkDetail(personId: String, workDetail: DailyWork) {
        roomDataStore.insertWorkDetail(workDetail)
//        firebaseDataStore.insertWorkDetail(personId, workDetail)
    }

    override fun getAllWorkDetailsFor(uId: String): Flow<PersonWithWorkDetail> =
        roomDataStore.getAllWorkDetailsFor(uId)

    override fun getWorkInfo(uId: String, date: Date): PersonWithWorkDetail =
        roomDataStore.getWorkInfo(UUID.randomUUID(), date)
}