package com.digitaldairy.labour.workscreen

import android.app.Application
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.digitaldairy.labour.repo.ILabourRepository
import com.digitaldairy.labour.WagePreferencesManager
import com.digitaldairy.labour.data.model.DailyWork
import com.digitaldairy.labour.data.model.DailyWorkWithCategory
import com.digitaldairy.labour.data.model.DayOfTheWork
import com.digitaldairy.labour.data.model.WorkCategory
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
import java.util.UUID
import javax.inject.Inject

interface IWorkListingViewModel {
    fun addWorkEntry(personId: String, workDetail: DailyWork, onDone: () -> Unit)
    fun getAllWorkEntryOf(uId: String): StateFlow<List<DailyWorkWithCategory>>
    fun getAllWorkEntryOf(uId: String, date: Date): List<DailyWorkWithCategory>
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
        workDetail: DailyWork,
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

    override fun getAllWorkEntryOf(uId: String): StateFlow<List<DailyWorkWithCategory>> {
        return repository.getAllWorkDetailsFor(uId).map { it.workDetailList }.stateIn(
            scope = viewModelScope, // Or any CoroutineScope
            started = SharingStarted.WhileSubscribed(5000), // Defines when to start/stop
            initialValue = emptyList() // Initial state
        )
    }

    override fun getAllWorkEntryOf(uId: String, date: Date): List<DailyWorkWithCategory> {
        return repository.getWorkInfo(uId, date).workDetailList
    }

    companion object {
        fun getFake(withData: Boolean = true): IWorkListingViewModel {
            return object : IWorkListingViewModel {
                override fun addWorkEntry(
                    personId: String,
                    workDetail: DailyWork,
                    onDone: () -> Unit
                ) {

                }

                override fun getAllWorkEntryOf(uId: String): StateFlow<List<DailyWorkWithCategory>> {
                    return flow<List<DailyWorkWithCategory>> {
                        geDailyWorkWithCategory()
                    }.stateIn(
                        CoroutineScope(Dispatchers.Unconfined),
                        SharingStarted.Eagerly,
                        geDailyWorkWithCategory()
                    )
                }

                override fun getAllWorkEntryOf(
                    uId: String,
                    date: Date
                ): List<DailyWorkWithCategory> {
                    return geDailyWorkWithCategory()
                }

                fun geDailyWorkWithCategory(): List<DailyWorkWithCategory> {
                    return getData().map {
                        DailyWorkWithCategory(it, WorkCategory("category", "category", "category"))
                    }
                }

                fun getData(): List<DailyWork> {
                    return if (withData) {
                        listOf(
                            DailyWork(

                                UUID.randomUUID(),
                                Date(),
                                3,
                                "Land scaping", 200,

                                DayOfTheWork.MORNING,
                            ),
                            DailyWork(

                                UUID.randomUUID(),
                                Date(),
                                3,
                                "Land scaping2", 200,
                                DayOfTheWork.MORNING,
                            )
                        )
                    } else {
                        emptyList()
                    }
                }
            }
        }
    }
}