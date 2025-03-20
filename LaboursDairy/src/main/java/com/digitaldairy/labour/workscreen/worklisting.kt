package com.digitaldairy.labour.workscreen

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.digitaldairy.common.AppToolbar
import com.digitaldairy.common.ScreenTopLayout
import com.digitaldairy.compose.appcomponents.LabelValueText
import com.digitaldairy.labour.Screen
import com.digitaldairy.labour.data.dao.WorkDetailDao
import com.digitaldairy.labour.data.model.WorkDetail
import com.digitaldairy.labour.usecase.WorkDetailUsecase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date

//@Preview
//@Composable
//fun LabourWorkListPreview() {
//    LabourWorkList(WorkListingViewModel(Application(), WorkDetailUsecase(object : WorkDetailDao {
//        override fun getAllAsLiveData(): Flow<List<WorkDetail>> {
//            return flow {
//                mutableListOf(
//                    WorkDetail(
//                        "test",
//                        Date(),
//                        1,
//                        "test",
//                        false,
//                        0,
//                        0
//                    )
//                )
//            } //<List<WorkDetail>>(arrayListOf())
//        }
//
//        override fun loadAllById(userId: String): Flow<List<WorkDetail>> {
//            return flow {
//                mutableListOf(
//                    WorkDetail(
//                        "test",
//                        Date(),
//                        1,
//                        "test",
//                        false,
//                        0,
//                        0
//                    )
//                )
//            } //<List<WorkDetail>>(arrayListOf())
//        }
//
//        override fun getWorkDetail(userId: String, date: Date): List<WorkDetail> {
//            return mutableListOf(
//                WorkDetail(
//                    "test",
//                    Date(),
//                    1,
//                    "test",
//                    false,
//                    0,
//                    0
//                )
//            ) //<List<WorkDetail>>(arrayListOf())
//
//        }
//
//        override suspend fun insert(vararg workDetail: WorkDetail) {
//            TODO("Not yet implemented")
//        }
//
//        override suspend fun update(vararg workDetail: WorkDetail) {
//            TODO("Not yet implemented")
//        }
//
//        override suspend fun delete(workDetail: WorkDetail) {
//            TODO("Not yet implemented")
//        }
//
//    })), rememberNavController(), "")
//}

@Composable
fun LabourWorkList(
    workListingViewModel: WorkListingViewModel =
        hiltViewModel(), navController: NavHostController, userId: String
) {
    val entries = workListingViewModel.getAllWorkEntryOf(userId).collectAsState(emptyList())
    ScreenTopLayout(
        screen = Screen.LabourWorkListing,
        topBar = {
            AppToolbar(
                "Work List",
                Screen.LabourWorkListing,
                navController = navController
            )
        },
        navController = navController,
        showFloatingActionButton = true,
        { navController.navigate("${Screen.LabourWorkEntry.screenName}/$userId") }
    ) {
        if (entries.value == null || entries.value?.size == 0) {
            Text("There are no entries", color = MaterialTheme.colorScheme.onTertiary)
        } else {
            LazyColumn {
                items(entries.value!!) {
                    val paddingModifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                    Card(
                        elevation = 10.dp,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                        modifier = paddingModifier.clickable {
                            navController.navigate("${Screen.LabourWorkEntry.screenName}/$userId?date=${it.date.time}")
                        }) {
                        Column(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.tertiary)
                                .padding(10.dp)
                        ) {
                            val formatter = SimpleDateFormat("dd/MM/yyyy")
                            val date = formatter.format(it.date)

                            LabelValueText("Date", date)
                            LabelValueText("WorkDescription", it.workDescription)
                            LabelValueText("Is Paid", it.isPaid.toString())
                            LabelValueText("DailyWage", it.dailyWage.toString())
                            LabelValueText("AmountPaid", it.amountPaid.toString())
                            LabelValueText("Hours", it.hours.toString())
                        }
                    }
                }
            }
        }
    }
}