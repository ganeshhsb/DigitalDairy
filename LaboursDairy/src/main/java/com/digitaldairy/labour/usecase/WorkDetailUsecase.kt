package com.digitaldairy.labour.usecase

import com.digitaldairy.labour.data.dao.WorkDetailDao
import com.digitaldairy.labour.data.model.DailyWork
import com.digitaldairy.labour.data.model.PersonWithWorkDetail
import com.digitaldairy.labour.data.model.WorkDetail
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class WorkDetailUsecase @Inject constructor(val dao: WorkDetailDao) {
    suspend fun insert(workDetail: DailyWork) {
        dao.insert(workDetail)
    }

    suspend fun update(workDetail: DailyWork) {
        dao.update(workDetail)
    }

    suspend fun delete(workDetail: DailyWork) {
        dao.delete(workDetail)
    }

//    fun getAllAsLiveData(uid: String): Flow<List<WorkDetail>> {
//        return dao.loadAllById(uid)
//    }

    fun getWorkInfo(uid: String, date: Date): PersonWithWorkDetail {
        return dao.getWorkDetail(UUID.randomUUID())
    }
}