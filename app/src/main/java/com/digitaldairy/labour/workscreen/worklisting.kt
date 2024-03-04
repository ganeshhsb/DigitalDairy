package com.digitaldairy.labour.workscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.digitaldairy.common.AppToolbar
import com.digitaldairy.common.ScreenTopLayout
import com.digitaldairy.labour.Screen
import java.text.SimpleDateFormat


@Composable
fun LabourWorkList(
    workListingViewModel: WorkListingViewModel =
        hiltViewModel(), navController: NavHostController, userId: String
) {
    val entries = workListingViewModel.getAllWorkEntryOf(userId).observeAsState()
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
            Text("There are no entries")
        } else {
            LazyColumn {
                items(entries.value!!) {
                    val paddingModifier = Modifier.fillMaxWidth().padding(10.dp)
                    Card(elevation = 10.dp, modifier = paddingModifier.clickable {
                                                                                 navController.navigate("${Screen.LabourWorkEntry.screenName}/$userId?date=${it.date.time}")
                    }, border = BorderStroke(1.dp, Color.Black)) {
                        Column(modifier = Modifier.padding(10.dp)) {
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