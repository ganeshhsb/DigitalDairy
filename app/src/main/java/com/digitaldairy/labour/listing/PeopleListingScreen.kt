package com.ganesh.compose.listing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.digitaldairy.common.AppToolbar
import com.digitaldairy.common.ScreenTopLayout
import com.digitaldairy.labour.Screen
import com.digitaldairy.labour.data.model.People
import com.digitaldairy.labour.workscreen.AppTextField
import com.digitaldairy.labour.workscreen.LabelValueText
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
        title = "Edit labor details"
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
        title = "Create new labor"
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
                label = "First Name : "
            ) {
                peopleState.value.firstName =
                    it
            }

            AppTextField(
                peopleState.value.lastName,
                label = "Last Name : "
            ) {
                peopleState.value.lastName =
                    it
            }
        }
    }
}

@Composable
fun PeopleDetailScreen(
    peopleListingViewModel: PeopleListingViewModel = hiltViewModel(),
    navController: NavHostController,
    userId: String
) {

    ScreenTopLayout(
        screen = Screen.DetailScreen,
        topBar = {
            AppToolbar(
                title = "Labor detail",
                currentScreen = Screen.DetailScreen,
                navController = navController,
                onEditClick = {
                    navController.navigate("${Screen.EditScreen.screenName}/${userId}")
                }
            )
        },
        navController = navController
    )
    {
        val personState = peopleListingViewModel.peopleListLiveData.observeAsState(
            emptyList()
        )

        val people = personState.value.firstOrNull { it.uid == userId }

        if (personState.value.isEmpty() || people == null) {
            LoadingScreen()
        } else {
            Column(modifier = Modifier.fillMaxWidth().clickable {
                navController.navigate("${Screen.LabourWorkListing.screenName}/${people?.uid}")
            }) {
                val paddingModifier = Modifier.fillMaxWidth().padding(10.dp)
                Card(
                    elevation = 10.dp,
                    modifier = paddingModifier,
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    LabelValueText("Name", people.firstName + " " + people.lastName, modifier = Modifier.padding(10.dp))
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.width(30.dp).height(30.dp))
    }
}