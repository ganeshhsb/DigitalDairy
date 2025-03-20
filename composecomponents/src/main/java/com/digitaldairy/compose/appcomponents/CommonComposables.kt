package com.digitaldairy.compose.appcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.tooling.preview.Preview
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
        Icon(
            imageVector = imageVector,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onTertiary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePickerDialog(
    showDatePicker: MutableState<Boolean>,
    selectedDate: MutableState<Long>,
    datePickerState: DatePickerState,
    onDateSelection: (date: Date) -> Unit
) {
    DatePickerDialog(
        onDismissRequest = {
            showDatePicker.value = false
        },
        confirmButton = {
            TextButton(onClick = {


                showDatePicker.value = false
                selectedDate.value = datePickerState.selectedDateMillis ?: 0
                onDateSelection(Date(datePickerState.selectedDateMillis ?: 0))
            }) {
                Text(text = "Confirm", color = MaterialTheme.colorScheme.onTertiary)
            }
        },
        dismissButton = {
            TextButton(onClick = {
                showDatePicker.value = false
            }) {
                Text(text = "Cancel", color = MaterialTheme.colorScheme.onTertiary)
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
            text = "Selected date: ${formatter.format(Date(datePickerState.selectedDateMillis!!))}",
            color = MaterialTheme.colorScheme.onTertiary
        )

//        Text(
//            text = "Selected Date: ${SimpleDateFormat("dd/MM/yyyy").format(selectedDate.time)}",
//            modifier = Modifier.padding(16.dp)
//        )
    }
}

@Composable
fun AppCheckbox(isChecked: Boolean, onCheckChanged: (isChecked: Boolean) -> Unit) {
    key(isChecked) {
        val checkedState = remember { mutableStateOf(isChecked) }
        Checkbox(
            checkedState.value,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary, // Color when checked
                uncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), // Color when unchecked
                checkmarkColor = MaterialTheme.colorScheme.onPrimary, // Checkmark color
                disabledCheckedColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), // Disabled checked
                disabledUncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f), // Disabled unchecked
            ),
            onCheckedChange = {
                checkedState.value = it
                onCheckChanged(it)
            }
        )
    }
}

@Preview
@Composable
fun AppTextField(
    value: String = "",
    label: String = "",
    placeHolder: String = "",
    modifier: Modifier = Modifier,
    onValueChange: (text: String) -> Unit = {},
) {
    key(value) {
        val textFieldValueState = remember { mutableStateOf(value) }
        TextField(
            modifier = modifier.background(color = MaterialTheme.colorScheme.tertiary),
            value = textFieldValueState.value,
            onValueChange = { text: String ->
                textFieldValueState.value = text
                onValueChange(text)
            },

            textStyle = TextStyle(color = MaterialTheme.colorScheme.onTertiary),
            label = {
                Text(
                    text = label, color = MaterialTheme.colorScheme.onTertiary,
                    fontSize = TextUnit(14.0F, TextUnitType.Sp)
                )
            },
            placeholder = {
                Text(
                    text = placeHolder, color = MaterialTheme.colorScheme.onTertiary,
                    fontSize = TextUnit(14.0F, TextUnitType.Sp)
                )
            },
            shape = RoundedCornerShape(5.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.tertiary, // Hide default underline
                unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                cursorColor = Color.White, // Change cursor color
                unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                focusedContainerColor = MaterialTheme.colorScheme.tertiary
            )
        )
    }
}

@Composable
fun AppText(
    value: String,
    modifier: Modifier = Modifier,
    fontSize: Float = 14f,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    textStyle: TextStyle = LocalTextStyle.current
) {
    Text(
        color = MaterialTheme.colorScheme.onTertiary,
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
    value: String,
    modifier: Modifier = Modifier.background(MaterialTheme.colorScheme.tertiary),
    fontSize: Float = 14f,
    fontWeight: FontWeight? = null,
) {
    Text(
        color = MaterialTheme.colorScheme.onSecondary,
        text = value,
        modifier = modifier,
        fontSize = TextUnit(fontSize, type = TextUnitType.Sp),
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
fun ValueText(
    value: String,
    modifier: Modifier = Modifier.background(MaterialTheme.colorScheme.tertiary),
    fontSize: Float = 14f,
    fontWeight: FontWeight? = null,
) {
    Text(
        color = MaterialTheme.colorScheme.onSecondary,
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
    value: String, modifier: Modifier = Modifier, fontSize: Float = 16f,
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
    value: String, modifier: Modifier = Modifier, fontSize: Float = 16f,
    fontWeight: FontWeight? = null,
) {
    Row(modifier = modifier) {
        LabelText(label, fontSize = fontSize)
        Text(" :  ", color = MaterialTheme.colorScheme.onTertiary)
        ValueText(value, fontSize = fontSize)
    }
}