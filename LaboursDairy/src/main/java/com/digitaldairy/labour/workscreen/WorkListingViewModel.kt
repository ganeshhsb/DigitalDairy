package com.digitaldairy.labour.workscreen

import android.app.Application
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.digitaldairy.labour.LabourRepository
import com.digitaldairy.labour.data.model.WorkDetail
import com.digitaldairy.labour.usecase.WorkDetailUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class WorkListingViewModel @Inject constructor(
    application: Application,
    private var repository: LabourRepository
) : AndroidViewModel(application) {
    @OptIn(ExperimentalMaterial3Api::class)
    var workEntryDate: MutableLiveData<DatePickerState> = MutableLiveData()

//    fun addWorkEntry(
//        uid: String,
//        date: Date,
//        hours: Int,
//        workDescription: String,
//        isPaid: Boolean,
//        dailyWage: Int
//    ) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val workDetail = WorkDetail(uid, date, hours, workDescription, isPaid, dailyWage)
//            repository.insertWorkDetail(uid, workDetail)
//        }
//    }

    fun addWorkEntry(
        personId:String,
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
//            workDetail.uid = personId + "---" + workDetail.date
            repository.insertWorkDetail(personId, workDetail)
            withContext(Dispatchers.Main) {
                onDone()
            }
        }
    }

    fun getAllWorkEntryOf(uId: String): StateFlow<List<WorkDetail>> {
        return repository.getAllWorkDetailsFor(uId).stateIn(
            scope = viewModelScope, // Or any CoroutineScope
            started = SharingStarted.WhileSubscribed(5000), // Defines when to start/stop
            initialValue = emptyList() // Initial state
        )
    }

    fun getAllWorkEntryOf(uId: String, date:Date): List<WorkDetail> {
        return repository.getWorkInfo(uId, date)
    }
}