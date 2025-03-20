package com.digitaldairy.labour.listing

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.digitaldairy.labour.R
import com.digitaldairy.common.AppToolbar
import com.digitaldairy.common.ScreenTopLayout
import com.digitaldairy.labour.data.model.Person
import com.digitaldairy.compose.appcomponents.LabelValueText
import com.digitaldairy.compose.appcomponents.theme.DigitalDairyTheme
import com.digitaldairy.labour.Screen


@Composable
fun PersonListingScreen(
    personListingViewModel: PersonListingViewModel = hiltViewModel(),
    navController: NavHostController,
    callback: (userId: String) -> Unit
) {
    val state =
        personListingViewModel.personListLiveData.collectAsState(emptyList()) // remember { peopleListingViewModel.peopleListLiveData }
    if (state.value == null) {
        LoadingScreen()
    } else {
        DigitalDairyTheme {
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
fun LaborItem(person: Person, callback: ((userId: String) -> Unit)? = null) {
    val paddingModifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
    Card(
        elevation = 10.dp,
        modifier = paddingModifier,
        border = BorderStroke(1.dp, Color.Black),
        backgroundColor = MaterialTheme.colorScheme.tertiary
    ) {
        Column(Modifier
            .padding(8.dp)
            .clickable {
                callback?.invoke(person.uid)
            }) {

            LabelValueText(
                stringResource(R.string.name),
                person.firstName + " " + person.lastName,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )

            LabelValueText(
                stringResource(R.string.age),
                person.age.toString(),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun LaborItemPreview() {
    DigitalDairyTheme {
        LaborItem(
            Person(
                firstName = "test", lastName = "test",
                uid = "",
                age = 12, phoneNumber = "",
                address = "",
                sex = ""
            )
        ) {}
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
        )
    }
}