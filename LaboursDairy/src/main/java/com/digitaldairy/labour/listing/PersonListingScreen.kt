package com.digitaldairy.labour.listing

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.digitaldairy.common.AppToolbar
import com.digitaldairy.common.ScreenTopLayout
import com.digitaldairy.compose.appcomponents.LabelValueText
import com.digitaldairy.compose.appcomponents.theme.DigitalDairyTheme
import com.digitaldairy.R
import com.digitaldairy.labour.Screen
import com.digitaldairy.labour.data.model.Person


@Composable
fun PersonListingScreen(
    personListingViewModel: IPersonListingViewModel = hiltViewModel<PersonListingViewModel>(),
    navController: NavHostController,
    callback: (userId: String) -> Unit
) {
    val state =
        personListingViewModel.personFlow.collectAsStateWithLifecycle(emptyList()) // remember { peopleListingViewModel.peopleListLiveData }

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
            if (state.value.isEmpty()) {
                LoadingScreen()
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    LazyColumn {
                        state.value.forEach {
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
                callback?.invoke(person.personId.toString())
            }) {

            LabelValueText(
                stringResource(R.string.name),
                person.firstName + " " + person.lastName,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )

//            LabelValueText(
//                stringResource(R.string.age),
//                person.age.toString(),
//                modifier = Modifier
//                    .padding(8.dp)
//                    .fillMaxWidth()
//            )
        }
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
                .size(30.dp)
                .testTag("loading_indicator"), strokeWidth = 4.dp

        )
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PersonListingScreen(@PreviewParameter(PersonPreviewProvider::class) personListingViewModel: IPersonListingViewModel) {
    DigitalDairyTheme {
        PersonListingScreen(personListingViewModel, rememberNavController()) {}
    }
}