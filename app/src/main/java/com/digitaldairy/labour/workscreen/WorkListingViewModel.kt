package com.digitaldairy.labour.workscreen

import android.app.Application
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import com.digitaldairy.labour.data.model.WorkDetail
import com.digitaldairy.labour.usecase.WorkDetailUsecase
import com.ganesh.compose.usecase.PeopleUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class WorkListingViewModel @Inject constructor(
    application: Application,
    var workDetailUsecase: WorkDetailUsecase
) : AndroidViewModel(application) {
    @OptIn(ExperimentalMaterial3Api::class)
    var workEntryDate: MutableLiveData<DatePickerState> = MutableLiveData()

    fun addWorkEntry(
        uid: String,
        date: Date,
        hours: Int,
        workDescription: String,
        isPaid: Boolean,
        dailyWage: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val workDetail = WorkDetail(uid, date, hours, workDescription, isPaid, dailyWage)
            workDetailUsecase.insert(workDetail)
        }
    }

    fun addWorkEntry(
        workDetail: WorkDetail,
        onDone: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val startOfDayCalendar = Calendar.getInstance().apply {
                timeInMillis = workDetail.date.time ?: 0
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            workDetail.date = Date(startOfDayCalendar.timeInMillis)
            workDetailUsecase.insert(workDetail)
            withContext(Dispatchers.Main) {
                onDone()
            }
        }
    }

    fun getAllWorkEntryOf(uId: String): LiveData<List<WorkDetail>> {
        return workDetailUsecase.getAllAsLiveData(uId)
    }
}