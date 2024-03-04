package com.digitaldairy.labour.workscreen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.digitaldairy.R
import com.digitaldairy.common.AppDatePickerDialog
import com.digitaldairy.common.AppToolbar
import com.digitaldairy.common.ScreenTopLayout
import com.digitaldairy.labour.Screen
import com.digitaldairy.labour.data.model.WorkDetail
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

@Composable
fun AppCheckbox(isChecked: Boolean, onCheckChanged: (isChecked: Boolean) -> Unit) {
    key(isChecked){
        val checkedState = remember { mutableStateOf(isChecked) }
        Checkbox(
            checkedState.value,
            onCheckedChange = {
                checkedState.value = it
                onCheckChanged(it)
            }
        )
    }
}

@Composable
fun AppTextField(
    value: String,
    label: String = "",
    placeHolder: String = "",
    onValueChange: (text: String) -> Unit,
) {
    key(value){
        val textFieldValueState = remember { mutableStateOf(value) }
        TextField(modifier = Modifier.background(color = MaterialTheme.colorScheme.tertiary),
            value = textFieldValueState.value,
            onValueChange = { text: String ->
                textFieldValueState.value = text
                onValueChange(text)
            },

            textStyle = TextStyle(color = MaterialTheme.colorScheme.onTertiary),
            label = {
                Text(
                    text = label, color = Color.Black,
                    fontSize = TextUnit(14.0F, TextUnitType.Sp)
                )
            },
            placeholder = {
                Text(
                    text = placeHolder, color = Color.Black,
                    fontSize = TextUnit(14.0F, TextUnitType.Sp)
                )
            },
            shape = RoundedCornerShape(5.dp)
        )
    }
}

@Composable
fun AppText(
    value: String, modifier: Modifier = Modifier, fontSize: Float = 14f,
    fontWeight: FontWeight? = null,textAlign:TextAlign? = null, textStyle: TextStyle = LocalTextStyle.current
) {
    Text(
        text = value,
        modifier = modifier,
        fontSize = TextUnit(fontSize, type = TextUnitType.Sp),
        fontFamily = FontFamily.SansSerif,
        fontWeight = fontWeight,
        textAlign = textAlign,
        style = textStyle
    )
}

@Composable
fun LabelText(
    value: String, modifier: Modifier = Modifier, fontSize: Float = 14f,
    fontWeight: FontWeight? = null,
) {
    Text(
        text = value,
        modifier = modifier,
        fontSize = TextUnit(fontSize, type = TextUnitType.Sp),
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
fun ValueText(
    value: String, modifier: Modifier = Modifier, fontSize: Float = 14f,
    fontWeight: FontWeight? = null,
) {
    Text(
        text = value,
        modifier = modifier,
        fontSize = TextUnit(fontSize, type = TextUnitType.Sp),
        fontFamily = FontFamily.SansSerif,
        fontWeight = fontWeight,
    )
}

@Composable
fun BoldText(
    value: String, modifier: Modifier = Modifier, fontSize: Float = 14f,
    fontWeight: FontWeight? = null,
) {
    Text(
        text = value,
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        fontSize = TextUnit(fontSize, type = TextUnitType.Sp),
    )
}


@Composable
fun HeaderText(
    value: String, modifier: Modifier = Modifier, fontSize: Float = 14f,
    fontWeight: FontWeight? = null,
) {
    Text(
        text = value,
        modifier = modifier,
        fontSize = TextUnit(18f, type = TextUnitType.Sp),
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif
    )
}

@Composable
fun LabelValueText(
    label: String,
    value: String, modifier: Modifier = Modifier, fontSize: Float = 14f,
    fontWeight: FontWeight? = null,
) {
    Row(modifier = modifier) {
        LabelText(label, fontSize = fontSize)
        Text(" :  ")
        ValueText(value, fontSize = fontSize)
    }
}

