package com.digitaldairy.labour.usecase

import androidx.lifecycle.LiveData
import com.digitaldairy.labour.data.dao.WorkDetailDao
import com.digitaldairy.labour.data.model.WorkDetail
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

    fun getAllAsLiveData(uid: String): LiveData<List<WorkDetail>> {
        return dao.loadAllById(uid)
    }

    fun getWorkInfo(uid: String, date: Date): List<WorkDetail> {
        return dao.getWorkDetail(uid, date)
    }
}