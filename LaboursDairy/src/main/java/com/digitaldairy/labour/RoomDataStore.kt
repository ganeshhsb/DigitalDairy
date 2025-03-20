package com.digitaldairy.labour

import com.digitaldairy.labour.data.AppDatabase
import com.digitaldairy.labour.data.model.Person
import com.digitaldairy.labour.data.model.PersonWithWorkDetail
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
    fun getAllPersonsAsWorkflow() = appDatabase.personDao().getAllAsLiveData()
    suspend fun insertPerson(person: Person) = appDatabase.personDao().insert(person)
    suspend fun insertWorkDetail(workDetail: WorkDetail) {
        appDatabase.workDetailDao().insert(workDetail)
    }

    fun getAllWorkDetailsFor(uId: String): Flow<PersonWithWorkDetail> =
        appDatabase.workDetailDao().getAllAsLiveData(uId)

    fun getWorkInfo(uId: String, date: Date): PersonWithWorkDetail =
        appDatabase.workDetailDao().getWorkDetail(uId )
}