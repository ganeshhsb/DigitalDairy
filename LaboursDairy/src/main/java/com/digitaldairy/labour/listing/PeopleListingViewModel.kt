package com.digitaldairy.labour.listing

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.digitaldairy.labour.LabourRepository
import com.digitaldairy.labour.data.model.People
import com.digitaldairy.labour.usecase.PeopleUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class PeopleListingViewModel @Inject constructor(
    application: Application,
    var repository: LabourRepository
) : AndroidViewModel(application) {
    //    @Inject
//    lateinit var peopleUsecase: PeopleUsecase
    var peopleListLiveData: StateFlow<List<People>> = repository.getPeopleAllDataAsFlow().stateIn(
        scope = viewModelScope, // Or any CoroutineScope
        started = SharingStarted.WhileSubscribed(5000), // Defines when to start/stop
        initialValue = emptyList() // Initial state
    )

    fun insertPeople(people: People) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertPeople(people)
        }
    }

    fun getAllPeople(): Flow<List<People>> {
        //CoroutineScope(Dispatchers.IO).launch {
//        if(peopleListLiveData == null){
//            peopleListLiveData = peopleUsecase.getAllAsLiveData().
//        }
        return repository.getPeopleAllDataAsFlow()
//        }
    }

    fun saveData(people: People, onDone: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertPeople(people)
            withContext(Dispatchers.Main) {
                onDone()
            }
        }
    }
}