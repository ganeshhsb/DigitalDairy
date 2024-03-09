package com.digitaldairy.labour.workscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.digitaldairy.common.AppToolbar
import com.digitaldairy.common.ScreenTopLayout
import com.digitaldairy.labour.data.model.WorkDetail
import com.digitaldairy.compose.appcomponents.AppCheckbox
import com.digitaldairy.compose.appcomponents.AppDatePickerDialog
import com.digitaldairy.compose.appcomponents.AppText
import com.digitaldairy.compose.appcomponents.AppTextField
import com.digitaldairy.compose.appcomponents.LabelValueText
import com.digitaldairy.labour.R
import com.digitaldairy.labour.Screen
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LabourWorkEntry(
    workListingViewModel: WorkListingViewModel =
        hiltViewModel(),
    navController: NavHostController,
    userId: String,
    date: Date? = null
) {
    val scope = rememberCoroutineScope()
    val workDetailState:MutableState<WorkDetail?> = remember { mutableStateOf(WorkDetail(userId, Date(), 6, "", false, 200)) }
    LaunchedEffect("Test") {

        if (date == null) {
             workDetailState.value = WorkDetail(userId, Date(), 6, "", false, 200)
        } else {
            workListingViewModel.viewModelScope.launch(Dispatchers.IO) {
                val data = workListingViewModel.getAllWorkEntryOf(userId, date).first()
                scope.launch {
                    workDetailState.value = data
                }
            }
            }
    }

    if (workDetailState.value != null) {
        WorkDetailContent(
            navController,
            workListingViewModel,
            workDetailState.value!!,
            userId
        )
    } else {
        AppText("Loading")
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun WorkDetailContent(
    navController: NavHostController,
    workListingViewModel: WorkListingViewModel,
    workDetailState: WorkDetail,
    userId: String
) {
    key(workDetailState){
        ScreenTopLayout(
            screen = Screen.LabourWorkEntry,
            topBar = {
                AppToolbar(
                    stringResource(R.string.work_entry),
                    Screen.LabourWorkEntry,
                    navController = navController,
                    onDoneClick = {
                        if (workDetailState != null) {
                            workListingViewModel.addWorkEntry(workDetailState!!) {
                                navController.popBackStack()
                            }
                        } else {
                            navController.popBackStack()
                        }
                    }, onCancelClick = {
                        navController.popBackStack()
                    }
                )
            },
            navController = navController,
            showFloatingActionButton = false,
            { navController.navigate("${Screen.LabourWorkEntry.screenName}/$userId") }
        ) {
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

                Column {

                    Row {
                        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.ROOT)
                        val date = formatter.format(selectedDate.value)
                        LabelValueText(stringResource(R.string.selected_date), date)
                        Spacer(modifier = Modifier.width(1.dp).height(16.dp))
                        Button(modifier = Modifier.padding(top = 1.dp),
                            onClick = {
                                showDatePicker.value = true
                            }
                        ) {
                            Text(text = stringResource(R.string.show_date_picker))
                        }
                    }
                    AppTextField(
                        workDetailState?.hours.toString(),
                        stringResource(R.string.hours_worked)
                    ) {
                        if (it.isNotEmpty() && it.isDigitsOnly()) {
                            workDetailState?.hours = it.toInt()
                        }
                    }

                    AppTextField(
                        workDetailState?.dailyWage.toString(),
                        stringResource(R.string.work_description),
                        stringResource(R.string.enter_work_description)
                    ) {
                        workDetailState?.workDescription = it
                    }

                    AppCheckbox(workDetailState?.isPaid ?: false) {
                        workDetailState?.isPaid = it
                    }

                    AppTextField(
                        workDetailState?.dailyWage.toString(),
                        stringResource(R.string.daily_wage),
                        stringResource(R.string.enter_daily_wage)
                    ) {
                        if (it.isNotEmpty() && it.isDigitsOnly()) {
                            workDetailState?.dailyWage = it.toInt()
                        }
                    }
                }
            }
        }
    }
}

