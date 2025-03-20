package com.digitaldairy.labour

import com.digitaldairy.labour.data.AppDatabase
import com.digitaldairy.labour.data.model.People
import com.digitaldairy.labour.data.model.WorkDetail
import kotlinx.coroutines.flow.Flow
import java.util.Date


class RoomDataStore(private val appDatabase: AppDatabase) {

    //    fun syncLabourFromFirestore(labourDao: LabourDao) {
//        db.collection("labours").get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    val labour = Labour(
//                        id = document.id,
//                        name = document.getString("name") ?: "",
//                        age = document.getLong("age")?.toInt() ?: 0,
//                        job = document.getString("job") ?: ""
//                    )
//                    CoroutineScope(Dispatchers.IO).launch {
//                        labourDao.insertLabour(labour)
//                    }
//                }
//            }
//    }
    fun getAllPeopleAsWorkflow() = appDatabase.peopleDao().getAllAsLiveData()
    suspend fun insertPeople(people: People) = appDatabase.peopleDao().insert(people)
    suspend fun insertWorkDetail(workDetail: WorkDetail) =
        appDatabase.workDetailDao().insert(workDetail)

    fun getAllWorkDetailsFor(uId: String): Flow<List<WorkDetail>> =
        appDatabase.workDetailDao().getAllAsLiveData()

    fun getWorkInfo(uId: String, date: Date): List<WorkDetail> =
        appDatabase.workDetailDao().getWorkDetail(uId, date)
}