package com.digitaldairy.labour.workscreen

import android.app.Application
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.digitaldairy.labour.repo.ILabourRepository
import com.digitaldairy.labour.WagePreferencesManager
import com.digitaldairy.labour.data.model.WorkDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

interface IWorkListingViewModel {
    fun addWorkEntry(personId: String, workDetail: WorkDetail, onDone: () -> Unit)
    fun getAllWorkEntryOf(uId: String): StateFlow<List<WorkDetail>>
    fun getAllWorkEntryOf(uId: String, date: Date): List<WorkDetail>
}

@HiltViewModel
class WorkListingViewModel @Inject constructor(
    application: Application,
    private var preferencesManager: WagePreferencesManager,
    private var repository: ILabourRepository
) : AndroidViewModel(application), IWorkListingViewModel {
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

    override fun addWorkEntry(
        personId: String,
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

    override fun getAllWorkEntryOf(uId: String): StateFlow<List<WorkDetail>> {
        return repository.getAllWorkDetailsFor(uId).map { it.workDetailList }.stateIn(
            scope = viewModelScope, // Or any CoroutineScope
            started = SharingStarted.WhileSubscribed(5000), // Defines when to start/stop
            initialValue = emptyList() // Initial state
        )
    }

    override fun getAllWorkEntryOf(uId: String, date: Date): List<WorkDetail> {
        return repository.getWorkInfo(uId, date).workDetailList
    }

    companion object {
        fun getFake(withData: Boolean = true): IWorkListingViewModel {
            return object : IWorkListingViewModel {
                override fun addWorkEntry(
                    personId: String,
                    workDetail: WorkDetail,
                    onDone: () -> Unit
                ) {

                }

                override fun getAllWorkEntryOf(uId: String): StateFlow<List<WorkDetail>> {
                    return flow<List<WorkDetail>> { getData() }.stateIn(
                        CoroutineScope(Dispatchers.Unconfined),
                        SharingStarted.Eagerly,
                        getData()
                    )
                }

                override fun getAllWorkEntryOf(uId: String, date: Date): List<WorkDetail> {
                    return getData()
                }

                fun getData(): List<WorkDetail> {
                    return if (withData) {
                        listOf(
                            WorkDetail("uid", Date(), 3, "Land scaping", false, 6, 0, "Ganeral"),
                            WorkDetail("uid", Date(), 3, "Land scaping2", false, 6, 0, "ABC")
                        )
                    } else {
                        emptyList()
                    }
                }
            }
        }
    }
}