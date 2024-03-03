package com.digitaldairy.labour.workscreen

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.digitaldairy.common.AppDatePickerDialog
import com.digitaldairy.common.AppToolbar
import com.digitaldairy.common.ScreenTopLayout
import com.digitaldairy.labour.Screen
import com.digitaldairy.labour.data.model.WorkDetail
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
@OptIn(ExperimentalMaterial3Api::class)

fun LabourWorkEntry(
    workListingViewModel: WorkListingViewModel =
        hiltViewModel(),
    navController: NavHostController,
    userId: String
) {
    val workDetail = remember { mutableStateOf(WorkDetail(userId, Date(), 6, "", false, 200)) }
    ScreenTopLayout(
        screen = Screen.LabourWorkEntry,
        topBar = {
            AppToolbar(
                "Work Entry",
                Screen.LabourWorkEntry,
                navController = navController,
                onDoneClick = {
                    workListingViewModel.addWorkEntry(workDetail.value) {
                        navController.popBackStack()
                    }
                }
            )
        },
        navController = navController,
        showFloatingActionButton = false,
        { navController.navigate("${Screen.LabourWorkEntry.screenName}/$userId") }
    ) {
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
                workDetail.value.date = it
            }
        }

        Column {

            Row {
                val formatter = SimpleDateFormat("dd MMM yyyy", Locale.ROOT)
                val date = formatter.format(selectedDate.value)
                Text(
                    text = "Selected date: $date"
                )
                Spacer(modifier = Modifier.width(1.dp).height(16.dp))
                Button(modifier = Modifier.padding(top = 1.dp),
                    onClick = {
                        showDatePicker.value = true
                    }
                ) {
                    Text(text = "Show Date Picker")
                }
            }
            AppTextField(
                workDetail.value.hours.toString(),
                "Hours worked: "
            ) {
                if (it.isNotEmpty() && it.isDigitsOnly()) {
                    workDetail.value.hours = it.toInt()
                }
            }

            AppTextField(
                workDetail.value.dailyWage.toString(),
                "Work description: ",
                "Enter work description"
            ) {
                workDetail.value.workDescription = it
            }

            AppCheckbox(workDetail.value.isPaid) {
                workDetail.value.isPaid = it
            }

            AppTextField(
                workDetail.value.dailyWage.toString(),
                "Daily wage: ",
                "Enter daily wage"
            ) {
                if (it.isNotEmpty() && it.isDigitsOnly()) {
                    workDetail.value.dailyWage = it.toInt()
                }
            }
        }
    }
}

@Composable
fun AppCheckbox(isChecked: Boolean, onCheckChanged: (isChecked: Boolean) -> Unit) {
    val checkedState = remember { mutableStateOf(isChecked) }
    Checkbox(
        checkedState.value,
        onCheckedChange = {
            checkedState.value = it
            onCheckChanged(it)
        }
    )
}

@Composable
fun AppTextField(
    value: String,
    label: String = "",
    placeHolder: String = "",
    onValueChange: (text: String) -> Unit,
) {
    val textFieldValueState = remember { mutableStateOf(value) }
    TextField(
        value = textFieldValueState.value,
        onValueChange = { text: String ->
            textFieldValueState.value = text
            onValueChange(text)
        },
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

@Composable
fun AppText(
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
    value: String, modifier: Modifier = Modifier, fontSize: Float =14f,
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
    Row {
        LabelText(label, fontSize = fontSize)
        Text(" :  ")
        ValueText(value, fontSize = fontSize)
    }
}

