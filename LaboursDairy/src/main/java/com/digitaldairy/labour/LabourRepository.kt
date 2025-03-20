package com.digitaldairy.labour

import com.digitaldairy.labour.data.model.Person
import com.digitaldairy.labour.data.model.PersonWithWorkDetail
import com.digitaldairy.labour.data.model.WorkDetail
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LabourRepository @Inject constructor(
    private val roomDataStore: RoomDataStore,
    private val firebaseDataStore: FirebaseDataStore
) {
    fun getAllPersonsDataAsFlow(): Flow<List<Person>> {
        return roomDataStore.getAllPersonsAsWorkflow()
    }

    suspend fun insertPerson(person: Person) {
        roomDataStore.insertPerson(person)
//        firebaseDataStore.insertPeople(people)
    }

    suspend fun insertWorkDetail(personId: String, workDetail: WorkDetail) {
        roomDataStore.insertWorkDetail(workDetail)
//        firebaseDataStore.insertWorkDetail(personId, workDetail)
    }

    fun getAllWorkDetailsFor(uId: String): Flow<PersonWithWorkDetail> =
        roomDataStore.getAllWorkDetailsFor(uId)

    fun getWorkInfo(uId: String, date: Date): PersonWithWorkDetail =
        roomDataStore.getWorkInfo(uId, date)
}