package com.digitaldairy.labour.listing

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.digitaldairy.common.AppToolbar
import com.digitaldairy.common.ScreenTopLayout
import com.digitaldairy.labour.data.model.People
import com.digitaldairy.compose.appcomponents.AppTextField
import com.digitaldairy.labour.R
import com.digitaldairy.labour.Screen
import java.util.UUID

@Composable
fun PeopleEntryScreen(
    peopleListingViewModel: PeopleListingViewModel =
        hiltViewModel(), navController: NavHostController, people: People? = null
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
            mutableStateOf(
                People(
                    UUID.randomUUID().toString(),
                    "Nagesh",
                    "shetty",
                    22,
                    "Bhadrapura"
                )
            )
        }
        screen = Screen.NewScreen
        title = stringResource(R.string.create_new_labor)
    }

    ScreenTopLayout(
        screen = screen,
        topBar = {
            AppToolbar(
                title = title,
                currentScreen = screen,
                navController = navController,
                onDoneClick = {
                    peopleListingViewModel.saveData(peopleState.value) {
                        navController.popBackStack()
                    }
                },
                onCancelClick = {
                    navController.popBackStack()
                }
            )
        },
        navController = navController
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
                peopleState.value.firstName,
                label = stringResource(R.string.first_name)
            ) {
                peopleState.value.firstName =
                    it
            }

            AppTextField(
                peopleState.value.lastName,
                label = stringResource(R.string.last_name)
            ) {
                peopleState.value.lastName =
                    it
            }
        }
    }
}