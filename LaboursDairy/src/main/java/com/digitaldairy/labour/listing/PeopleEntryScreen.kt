package com.digitaldairy.labour.listing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.digitaldairy.common.AppToolbar
import com.digitaldairy.common.ScreenTopLayout
import com.digitaldairy.compose.appcomponents.AppTextField
import com.digitaldairy.compose.appcomponents.theme.DigitalDairyTheme
import com.digitaldairy.labour.R
import com.digitaldairy.labour.Screen
import com.digitaldairy.labour.data.model.People
import java.util.UUID

@Composable
fun PeopleEntryScreen(
    peopleListingViewModel: PeopleListingViewModel? =
        hiltViewModel(), navController: NavHostController? = null, people: People? = null
) {
    val peopleState: MutableState<People>
    val screen: Screen
    var title = ""
    if (people != null) {
        peopleState = remember { mutableStateOf(people) }
        screen = Screen.EditScreen
        title = stringResource(R.string.edit_labor_details)
    } else {
        peopleState = remember {
            mutableStateOf(People(UUID.randomUUID().toString(), "", "", 0, "",""))
        }
        screen = Screen.NewScreen
        title = stringResource(R.string.create_new_labor)
    }
    DigitalDairyTheme {
        ScreenTopLayout(
            screen = screen,
            topBar = {
                AppToolbar(
                    title = title,
                    currentScreen = screen,
                    navController = navController!!,
                    onDoneClick = {
                        peopleState.value?.let {
                            peopleListingViewModel?.saveData(it) {
                                navController?.popBackStack()
                            }
                        }
                    },
                    onCancelClick = {
                        navController?.popBackStack()
                    }
                )
            },
            navController = navController!!
        ) {
            val color = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black
            )
            Column {
                AppTextField(
                    peopleState.value?.firstName ?: "",
                    label = stringResource(R.string.first_name),
                    "Enter first name",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    peopleState.value?.firstName =
                        it
                }

                AppTextField(
                    peopleState.value?.lastName ?: "",
                    label = stringResource(R.string.last_name),
                    "Enter last name",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    peopleState.value?.lastName =
                        it
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PeopleEntryScreenPreview() {
//    MainActivityContent(peopleListingViewModel) // Replace with the actual composable function name used in MainActivity
    PeopleEntryScreen(people = People("test", "testasfsdf", "testasfd", 23, "test",""))
}