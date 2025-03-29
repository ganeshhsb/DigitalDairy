package com.digitaldairy.labour.workscreen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.digitaldairy.common.AppToolbar
import com.digitaldairy.common.ScreenTopLayout
import com.digitaldairy.compose.appcomponents.AppCheckbox
import com.digitaldairy.compose.appcomponents.AppDatePickerDialog
import com.digitaldairy.compose.appcomponents.AppText
import com.digitaldairy.compose.appcomponents.AppTextField
import com.digitaldairy.compose.appcomponents.LabelValueText
import com.digitaldairy.compose.appcomponents.theme.DigitalDairyTheme
import com.digitaldairy.R
import com.digitaldairy.labour.Screen
import com.digitaldairy.labour.data.model.DailyWork
import com.digitaldairy.labour.data.model.DayOfTheWork
import com.digitaldairy.labour.data.model.WorkDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LabourWorkEntry(
    workListingViewModel: IWorkListingViewModel = hiltViewModel<WorkListingViewModel>(),
    navController: NavHostController,
    userId: String,
    date: Date? = null
) {
    DigitalDairyTheme {
        val scope = rememberCoroutineScope()
        val workDetailState: MutableState<DailyWork?> =
            remember {
                mutableStateOf(
                    DailyWork(
                        UUID.randomUUID(),
                        Date(),
                        6,
                        "",
                        200,  DayOfTheWork.MORNING,
                    )
                )
            }
        LaunchedEffect("Test") {

            if (date == null) {
                workDetailState.value =
                    DailyWork(UUID.randomUUID(), Date(), 6, "", 200,  DayOfTheWork.MORNING)
            } else {
                scope.launch(Dispatchers.IO) {
                    val data = workListingViewModel.getAllWorkEntryOf(userId, date).first()
                    scope.launch {
                        workDetailState.value = data.dailyWork
                    }
                }
            }
        }

        if (workDetailState.value != null) {
            WorkDetailContent(
                navController,
                workDetailState.value!!, userId
            ) {
                workListingViewModel.addWorkEntry(userId, workDetailState.value!!) {
                    navController.popBackStack()
                }
            }
        } else {
            AppText("Loading")
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun WorkDetailContent(
    navController: NavHostController,
    workDetailState: DailyWork,
    userId: String,
    onDoneClick: () -> Unit
) {
    key(workDetailState) {
        ScreenTopLayout(screen = Screen.LabourWorkEntry,
            topBar = {
                AppToolbar(stringResource(R.string.work_entry),
                    Screen.LabourWorkEntry,
                    navController = navController,
                    onDoneClick = {
                        if (workDetailState != null) {
                            onDoneClick()
                        } else {
                            navController.popBackStack()
                        }
                    },
                    onCancelClick = {
                        navController.popBackStack()
                    })
            },
            navController = navController,
            showFloatingActionButton = false,
            { navController.navigate("${Screen.LabourWorkEntry.screenName}/$userId") }) {
            if (workDetailState == null) {
                AppText("There is nothing to show")
            } else {
                val calendar = Calendar.getInstance()
                calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DATE)
                ) // add year, month (Jan), date

                // set the initial date
                val datePickerState =
                    rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)

                val showDatePicker = remember {
                    mutableStateOf(false)
                }

                val selectedDate = remember {
                    mutableLongStateOf(calendar.timeInMillis) // or use mutableStateOf(calendar.timeInMillis)
                }


                if (showDatePicker.value) {
                    AppDatePickerDialog(
                        showDatePicker = showDatePicker,
                        selectedDate = selectedDate,
                        datePickerState = datePickerState
                    ) {
                        workDetailState?.date = it
                    }
                }

                Column(modifier = Modifier.padding(8.dp)) {

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.tertiary)
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.ROOT)
                        val date = formatter.format(selectedDate.value)
                        LabelValueText(stringResource(R.string.selected_date), date)
                        Button(onClick = {
                            showDatePicker.value = true
                        }) {
                            Text(
                                color = MaterialTheme.colorScheme.onTertiary,
                                text = stringResource(R.string.show_date_picker),
                                modifier = Modifier
                                    .padding(top = 1.dp)
                                    .background(MaterialTheme.colorScheme.secondary)
                            )
                        }
                    }
                    AppTextField(
                        workDetailState?.hours.toString(),
                        stringResource(R.string.hours_worked),
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    ) {
                        if (it.isNotEmpty() && it.isDigitsOnly()) {
                            workDetailState?.hours = it.toInt()
                        }
                    }

                    AppTextField(
                        workDetailState?.dailyWage.toString(),
                        stringResource(R.string.work_description),
                        stringResource(R.string.enter_work_description),
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    ) {
                        workDetailState?.workDescription = it
                    }

//                    Row(
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier
//                            .padding(top = 8.dp)
//                            .background(color = MaterialTheme.colorScheme.tertiary)
//                            .fillMaxWidth()
//                    ) {
//                        AppText("IsPaid", modifier = Modifier.padding(start = 8.dp))
//                        AppCheckbox(workDetailState?.isPaid ?: false) {
//                            workDetailState?.isPaid = it
//                        }
//                    }

                    AppTextField(
                        workDetailState?.dailyWage.toString(),
                        stringResource(R.string.daily_wage),
                        stringResource(R.string.enter_daily_wage),
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    ) {
                        if (it.isNotEmpty() && it.isDigitsOnly()) {
                            workDetailState?.dailyWage = it.toInt()
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .background(color = MaterialTheme.colorScheme.tertiary)
                            .fillMaxWidth()
                    ) {
                        var showPopup by remember { mutableStateOf(false) }
                        var selectedCategory by remember { mutableStateOf("None") }
                        AppText("Category",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { showPopup = true })
                        AppText(
                            selectedCategory, modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { showPopup = true }
                        )
                        if (showPopup) {
                            CategorySelectionPopup(
                                onCategorySelected = { category ->
                                    selectedCategory = category // Update selected category
                                },
                                onDismiss = { showPopup = false }
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun CategorySelectionPopup(
    onCategorySelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val categories = listOf("General", "Household", "Farm")

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Select Category",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                categories.forEach { category ->
                    Button(
                        onClick = {
                            onCategorySelected(category) // Callback when clicked
                            onDismiss() // Close popup
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = category)
                    }
                }
            }
        }
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun LabourWorkEntryPreview(
    @PreviewParameter(WorkListingViewModelProvider::class) workListingViewModel: IWorkListingViewModel
) {
    LabourWorkEntry(
        workListingViewModel,
        navController = rememberNavController(),
        userId = "12345", Date()
    )
}
