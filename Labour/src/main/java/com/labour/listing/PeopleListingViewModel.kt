package com.ganesh.compose.listing

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ganesh.compose.data.model.People
import com.ganesh.compose.usecase.PeopleUsecase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class PeopleListingViewModel @Inject constructor(application: Application, var peopleUsecase: PeopleUsecase) : AndroidViewModel(application) {
//    @Inject
//    lateinit var peopleUsecase: PeopleUsecase
    var peopleListLiveData:LiveData<List<People>> = peopleUsecase.getAllAsLiveData()

    fun insertPeople(people: People){
        CoroutineScope(Dispatchers.IO).launch{
        peopleUsecase.insert(people)
        }
    }

    fun getAllPeople(): LiveData<List<People>> {
        //CoroutineScope(Dispatchers.IO).launch {
         return peopleListLiveData
//        }
    }

    fun saveData(people: People, onDone:()->Unit) {
        CoroutineScope(Dispatchers.IO).launch{
            peopleUsecase.insert(people)
            withContext(Dispatchers.Main){
                onDone()
            }
        }
    }
}