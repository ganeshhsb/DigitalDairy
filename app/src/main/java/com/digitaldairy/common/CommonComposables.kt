package com.digitaldairy.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.digitaldairy.labour.FloatingActionButtonCompose
import com.digitaldairy.labour.Screen
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ScreenTopLayout(
    screen: Screen,
    topBar: @Composable () -> Unit,
    navController: NavHostController,
    showFloatingActionButton: Boolean = false,
    floatingActionButtonCallback: (() -> Unit)? = null,
    content: @Composable () -> Unit

) {
    Scaffold(
        topBar = topBar,
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                content.invoke()
            }
        },

        floatingActionButton = {
            if (showFloatingActionButton) {
                FloatingActionButtonCompose {
                    floatingActionButtonCallback?.invoke()
                }
            }
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    title: String,
    currentScreen: Screen,
    navController: NavHostController,
    onDoneClick: (() -> Unit)? = null,
    onCancelClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
    onEditClick: (() -> Unit)? = null
) {
    val showMenu = currentScreen is Screen.LaborListing
    val canEdit = currentScreen is Screen.EditScreen || currentScreen is Screen.NewScreen || currentScreen is Screen.LabourWorkEntry
    val showEditIcon = currentScreen is Screen.DetailScreen
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (canEdit) {
                ToolbarButton(Icons.Filled.Close) { onCancelClick?.invoke() }
            } else if (showMenu) {
                ToolbarButton(Icons.Filled.Menu) { navController.popBackStack() }
            } else {
                ToolbarButton(Icons.Filled.ArrowBack) { navController.popBackStack() }
            }
        },
        actions = {
            if (canEdit)
                ToolbarButton(Icons.Filled.Done) { onDoneClick?.invoke() }
            else if (showEditIcon) {
                ToolbarButton(Icons.Filled.Edit) { onEditClick?.invoke() }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
//            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

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