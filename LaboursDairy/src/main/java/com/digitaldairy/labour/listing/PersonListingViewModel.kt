package com.digitaldairy.labour.listing

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.digitaldairy.labour.ILabourRepository
import com.digitaldairy.labour.LabourRepository
import com.digitaldairy.labour.data.model.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.withContext

interface IPersonListingViewModel {
    var personFlow: StateFlow<List<Person>>
    fun saveData(person: Person, onDone: () -> Unit)
}

@HiltViewModel
class PersonListingViewModel @Inject constructor(
    application: Application,
    var repository: ILabourRepository
) : AndroidViewModel(application), IPersonListingViewModel {
    override var personFlow: StateFlow<List<Person>> =
        repository.getAllPersonsDataAsFlow().stateIn(
            scope = viewModelScope, // Or any CoroutineScope
            started = SharingStarted.WhileSubscribed(5000), // Defines when to start/stop
            initialValue = emptyList() // Initial state
        )


    override fun saveData(person: Person, onDone: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertPerson(person)
            withContext(Dispatchers.Main) {
                onDone()
            }
        }
    }

    fun setPeople(list: List<Person>) {
        personFlow = flow<List<Person>> {  }.stateIn(TestScope(), SharingStarted.WhileSubscribed(), list)
    }

    companion object {

        public fun getFake(withData: Boolean = true): IPersonListingViewModel {
            return object : IPersonListingViewModel {
                override var personFlow: StateFlow<List<Person>> = flow<List<Person>> {

                }.stateIn(
                    scope = CoroutineScope(Dispatchers.Unconfined), // Or any CoroutineScope
                    started = SharingStarted.WhileSubscribed(5000), // Defines when to start/stop
                    initialValue = getData(withData)
                )

                override fun saveData(person: Person, onDone: () -> Unit) {
                }

            }
        }

        fun getData(withData: Boolean): List<Person> {
            return if (withData) {
                listOf(
                    Person("uid", "Nagesh", "shetty", 40, "Phone number", "Male", "Address"),
                    Person("uid", "Nagesh", "shetty", 40, "Phone number", "Male", "Address")

                ) // Initial state
            } else {
                emptyList<Person>()
            }
        }


    }
}