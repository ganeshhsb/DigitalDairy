package com.digitaldairy.labour.listing

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.digitaldairy.common.AppToolbar
import com.digitaldairy.common.ScreenTopLayout
import com.digitaldairy.compose.appcomponents.LabelValueText
import com.digitaldairy.compose.appcomponents.theme.DigitalDairyTheme
import com.digitaldairy.labour.R
import com.digitaldairy.labour.Screen


@Composable
fun PersonDetailScreen(
    personListingViewModel: IPersonListingViewModel = hiltViewModel<PersonListingViewModel>(),
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
                val personState = personListingViewModel.personFlow.collectAsStateWithLifecycle()

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
                        Card(
                            elevation = CardDefaults.cardElevation(5.dp),
                            modifier = paddingModifier,
                            border = BorderStroke(1.dp, Color.Black)
                        ) {
                            LabelValueText(
                                stringResource(R.string.name),
                                people.firstName + " " + people.lastName,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.tertiary)
                                    .padding(10.dp)
                            )

                            LabelValueText(
                                stringResource(R.string.age),
                                people.age.toString(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.tertiary)
                                    .padding(10.dp)
                            )

                            LabelValueText(
                                stringResource(R.string.phone_number),
                                people.phoneNumber,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.tertiary)
                                    .padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

class PersonPreviewProvider : PreviewParameterProvider<IPersonListingViewModel> {
    override val values = sequenceOf(
        PersonListingViewModel.getFake(),
        PersonListingViewModel.getFake(false)
    )
}

//@Preview(
//    name = "Default Preview",
//    showBackground = true, // Adds a background
//    backgroundColor = 0xFFFFFFFF, // White background (optional)
//    fontScale = 1.2f, // Slightly larger text
//    widthDp = 400, heightDp = 800, // Portrait phone size
//    device = Devices.PIXEL_4, // Specific device
//    uiMode = Configuration.UI_MODE_NIGHT_NO, // Light mode
//    locale = "en" // English locale
//)
@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PersonDetailScreen(
    @PreviewParameter(PersonPreviewProvider::class) personListingViewModel:IPersonListingViewModel
) {
    PersonDetailScreen(personListingViewModel, rememberNavController(), "uid")
}