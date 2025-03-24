package com.digitaldairy.labour

import com.digitaldairy.labour.data.model.Person
import com.digitaldairy.labour.listing.IPersonListingViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakePeopleListViewModel : IPersonListingViewModel {

    var _personFlow: MutableStateFlow<List<Person>> = MutableStateFlow<List<Person>>(
        emptyList()
    )
    override var personFlow: StateFlow<List<Person>> = _personFlow


    override fun saveData(person: Person, onDone: () -> Unit) {
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

    fun setPeople(peoples: List<Person>) {
        _personFlow.value = peoples
    }
}
