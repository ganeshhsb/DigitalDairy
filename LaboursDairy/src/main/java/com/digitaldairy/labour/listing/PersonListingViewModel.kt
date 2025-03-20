package com.digitaldairy.labour.listing

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.digitaldairy.labour.LabourRepository
import com.digitaldairy.labour.data.model.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class PersonListingViewModel @Inject constructor(
    application: Application,
    var repository: LabourRepository
) : AndroidViewModel(application) {
    var personListLiveData: StateFlow<List<Person>> = repository.getAllPersonsDataAsFlow().stateIn(
        scope = viewModelScope, // Or any CoroutineScope
        started = SharingStarted.WhileSubscribed(5000), // Defines when to start/stop
        initialValue = emptyList() // Initial state
    )


    fun saveData(person: Person, onDone: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertPerson(person)
            withContext(Dispatchers.Main) {
                onDone()
            }
        }
    }
}