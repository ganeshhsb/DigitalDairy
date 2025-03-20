package com.digitaldairy.labour.usecase

import com.digitaldairy.labour.data.dao.WorkDetailDao
import com.digitaldairy.labour.data.model.WorkDetail
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class WorkDetailUsecase @Inject constructor(val dao: WorkDetailDao) {
    suspend fun insert(workDetail: WorkDetail) {
        dao.insert(workDetail)
    }

    suspend fun update(workDetail: WorkDetail) {
        dao.update(workDetail)
    }

    suspend fun delete(workDetail: WorkDetail) {
        dao.delete(workDetail)
    }

    fun getAllAsLiveData(uid: String): Flow<List<WorkDetail>> {
        return dao.loadAllById(uid)
    }

    fun getWorkInfo(uid: String, date: Date): List<WorkDetail> {
        return dao.getWorkDetail(uid, date)
    }
}