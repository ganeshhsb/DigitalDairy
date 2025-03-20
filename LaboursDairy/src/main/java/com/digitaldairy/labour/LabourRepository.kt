package com.digitaldairy.labour

import com.digitaldairy.labour.data.model.People
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
    fun getPeopleAllDataAsFlow(): Flow<List<People>> {
        return roomDataStore.getAllPeopleAsWorkflow()
    }

    suspend fun insertPeople(people: People) {
        roomDataStore.insertPeople(people)
        firebaseDataStore.insertPeople(people)
    }

    suspend fun insertWorkDetail(personId: String, workDetail: WorkDetail) {
        roomDataStore.insertWorkDetail(workDetail)
        firebaseDataStore.insertWorkDetail(personId, workDetail)
    }

    fun getAllWorkDetailsFor(uId: String): Flow<List<WorkDetail>> =
        roomDataStore.getAllWorkDetailsFor(uId)

    fun getWorkInfo(uId: String, date: Date): List<WorkDetail> =
        roomDataStore.getWorkInfo(uId, date)
}