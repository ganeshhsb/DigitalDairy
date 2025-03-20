package com.digitaldairy.labour.listing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.digitaldairy.labour.R
import com.digitaldairy.common.AppToolbar
import com.digitaldairy.common.ScreenTopLayout
import com.digitaldairy.compose.appcomponents.LabelValueText
import com.digitaldairy.compose.appcomponents.theme.DigitalDairyTheme
import com.digitaldairy.labour.Screen


@Composable
fun PeopleDetailScreen(
    peopleListingViewModel: PeopleListingViewModel = hiltViewModel(),
    navController: NavHostController,
    userId: String
) {
    DigitalDairyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.tertiary
        ) {
            ScreenTopLayout(
                screen = Screen.DetailScreen,
                topBar = {
                    AppToolbar(
                        title = stringResource(R.string.labor_detail),
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
                val personState = peopleListingViewModel.peopleListLiveData.collectAsState()

                val people = personState.value.firstOrNull { it.uid == userId }

                if (personState.value.isEmpty() || people == null) {
                    LoadingScreen()
                } else {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.tertiary)
                        .clickable {
                            navController.navigate("${Screen.LabourWorkListing.screenName}/${people?.uid}")
                        }) {
                        val paddingModifier = Modifier
                            .fillMaxWidth()
                          //  .background(MaterialTheme.colorScheme.tertiary)
                            .padding(10.dp)
//                        Card(
//                            elevation = 10.dp,
//                            modifier = paddingModifier,
//                            //border = BorderStroke(1.dp, Color.Black)
//                        ) {
                            LabelValueText(
                                stringResource(R.string.name),
                                people.firstName + " " + people.lastName,
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.tertiary)
                                    .padding(10.dp)
                            )
//                        }
                    }
                }
            }
        }
    }
}