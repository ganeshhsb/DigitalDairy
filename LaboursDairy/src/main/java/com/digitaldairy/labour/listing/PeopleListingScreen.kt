package com.digitaldairy.labour.listing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.digitaldairy.labour.R
import com.digitaldairy.common.AppToolbar
import com.digitaldairy.common.ScreenTopLayout
import com.digitaldairy.labour.data.model.People
import com.digitaldairy.compose.appcomponents.LabelValueText
import com.digitaldairy.labour.Screen
import com.digitaldairy.labour.theme.HelloComposeTheme


@Composable
fun PeopleListingScreen(
    peopleListingViewModel: PeopleListingViewModel = hiltViewModel(),
    navController: NavHostController,
    callback: (userId: String) -> Unit
) {
    val state = remember { peopleListingViewModel.peopleListLiveData }.observeAsState()
    if (state.value == null) {
        LoadingScreen()
    } else {
        HelloComposeTheme {
            ScreenTopLayout(
                screen = Screen.LaborListing,
                topBar = {
                    AppToolbar(
                        stringResource(R.string.labor_listing),
                        Screen.LaborListing,
                        navController = navController
                    )
                },
                navController = navController,
                showFloatingActionButton = true,
                { navController.navigate(Screen.NewScreen.screenName) }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    LazyColumn {
                        state.value?.forEach {
                            item {
                                LaborItem(it, callback)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LaborItem(people: People, callback: ((userId: String) -> Unit)? = null) {
    val paddingModifier = Modifier.fillMaxWidth().padding(10.dp)
    Card(elevation = 10.dp, modifier = paddingModifier, border = BorderStroke(1.dp, Color.Black)) {
        Column(Modifier.padding(8.dp).clickable {
            callback?.invoke(people.uid)
        }) {

            LabelValueText(
                stringResource(R.string.name),
                people.firstName + " " + people.lastName,
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.White)
                    .fillMaxWidth()
            )

            LabelValueText(
                stringResource(R.string.age),
                people.age.toString(),
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.White)
                    .fillMaxWidth()
            )
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