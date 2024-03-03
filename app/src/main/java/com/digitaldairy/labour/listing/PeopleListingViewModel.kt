package com.ganesh.compose.listing

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.digitaldairy.labour.data.model.People
import com.ganesh.compose.usecase.PeopleUsecase
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
//        if(peopleListLiveData == null){
//            peopleListLiveData = peopleUsecase.getAllAsLiveData().
//        }
         return peopleUsecase.getAllAsLiveData()
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