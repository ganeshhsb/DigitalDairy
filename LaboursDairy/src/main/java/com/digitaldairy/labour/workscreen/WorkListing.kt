package com.digitaldairy.labour.workscreen

import android.content.res.Configuration
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.digitaldairy.common.AppToolbar
import com.digitaldairy.common.ScreenTopLayout
import com.digitaldairy.compose.appcomponents.LabelValueText
import com.digitaldairy.compose.appcomponents.theme.DigitalDairyTheme
import com.digitaldairy.labour.Screen
import java.text.SimpleDateFormat

@Composable
fun LabourWorkList(
    workListingViewModel: IWorkListingViewModel =
        hiltViewModel<WorkListingViewModel>(), navController: NavHostController, userId: String
) {
    DigitalDairyTheme {

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
}

class WorkListingViewModelProvider : PreviewParameterProvider<IWorkListingViewModel> {
    override val values = sequenceOf(
        WorkListingViewModel.getFake(),
        WorkListingViewModel.getFake(false)
    )
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun LabourWorkListPreview(
    @PreviewParameter(WorkListingViewModelProvider::class) workListingViewModel: IWorkListingViewModel
) {
    LabourWorkList(
        workListingViewModel,
        rememberNavController(),
        "uid"
    )
}