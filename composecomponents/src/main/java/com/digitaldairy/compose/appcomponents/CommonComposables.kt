package com.digitaldairy.compose.appcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun ToolbarButton(imageVector: ImageVector, callback: () -> Unit) {
    IconButton(onClick = callback) {
        Icon(imageVector = imageVector, contentDescription = "")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePickerDialog(
    showDatePicker: MutableState<Boolean>,
    selectedDate: MutableState<Long>,
    datePickerState: DatePickerState,
    onDateSelection:(date:Date)->Unit
) {
    DatePickerDialog(
        onDismissRequest = {
            showDatePicker.value = false
        },
        confirmButton = {
            TextButton(onClick = {



                showDatePicker.value = false
                selectedDate.value = datePickerState.selectedDateMillis ?: 0
                onDateSelection(Date(datePickerState.selectedDateMillis?:0))
            }) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                showDatePicker.value = false
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerCompose() {
    //var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
    val calendar = Calendar.getInstance()
    calendar.set(1990, 0, 22) // add year, month (Jan), date
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
//        DatePicker(
//            selectedDate = selectedDate,
//            onDateSelected = { date ->
//                selectedDate = date
//            },
//            keyboardActions = DateKeyboardActions()
//        )
        DatePicker(state = datePickerState)

        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.ROOT)
        Text(
            text = "Selected date: ${formatter.format(Date(datePickerState.selectedDateMillis!!))}"
        )

//        Text(
//            text = "Selected Date: ${SimpleDateFormat("dd/MM/yyyy").format(selectedDate.time)}",
//            modifier = Modifier.padding(16.dp)
//        )
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
    fontWeight: FontWeight? = null, textAlign: TextAlign? = null, textStyle: TextStyle = LocalTextStyle.current
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