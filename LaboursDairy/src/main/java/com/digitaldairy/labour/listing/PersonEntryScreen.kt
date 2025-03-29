package com.digitaldairy.labour.listing

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.digitaldairy.common.AppToolbar
import com.digitaldairy.common.ScreenTopLayout
import com.digitaldairy.compose.appcomponents.AppTextField
import com.digitaldairy.compose.appcomponents.theme.DigitalDairyTheme
import com.digitaldairy.R
import com.digitaldairy.labour.Screen
import com.digitaldairy.labour.data.model.Person
import com.digitaldairy.labour.data.model.SexType
import java.util.UUID

@Composable
fun PersonEntryScreen(
    personListingViewModel: IPersonListingViewModel? =
        hiltViewModel<PersonListingViewModel>(), navController: NavHostController? = null, userId: String? = null
) {
    val personState: MutableState<Person>
    val screen: Screen
    var title = ""
    val person: Person? =
        personListingViewModel?.personFlow?.collectAsStateWithLifecycle(emptyList())?.value?.firstOrNull { it.personId.toString() == userId }
    if (person != null) {
        personState = remember { mutableStateOf(person) }
        screen = Screen.EditScreen
        title = stringResource(R.string.edit_labor_details)
    } else {
        personState = remember {
            mutableStateOf(
                Person(
                    personId = UUID.randomUUID(),
                    firstName = "",
                    lastName = "",
                    age = 0,
                    phoneNumber = "",
                    sex = SexType.MALE,
                    address = ""
                )
            )
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
                        personState.value?.let {
                            personListingViewModel?.saveData(it) {
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
                    personState.value?.firstName ?: "",
                    label = stringResource(R.string.first_name),
                    "Enter first name",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    personState.value?.firstName =
                        it
                }

                AppTextField(
                    personState.value?.lastName ?: "",
                    label = stringResource(R.string.last_name),
                    "Enter last name",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    personState.value?.lastName =
                        it
                }

                AppTextField(
                    personState.value?.phoneNumber ?: "",
                    label = stringResource(R.string.phone_number),
                    "Enter phone number",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                ) {
                    personState.value.phoneNumber =
                        it
                }

                AppTextField(
                    personState.value?.age.toString() ?: "",
                    label = stringResource(R.string.age),
                    "Enter age",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                ) {
                    personState.value.age =
                        try {
                            it.toInt()
                        } catch (exception: Exception) {
                            0
                        }

                }

            }
        }
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PersonEntryScreenPreview(@PreviewParameter(PersonPreviewProvider::class) personListingViewModel: IPersonListingViewModel) {
    PersonEntryScreen(personListingViewModel, rememberNavController(),"uid")
}