package com.digitaldairy.labour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.digitaldairy.common.AppToolbar
import com.digitaldairy.common.ScreenTopLayout
import com.digitaldairy.labour.data.model.People
import com.ganesh.compose.listing.LoadingScreen
import com.ganesh.compose.listing.PeopleListingViewModel
import com.ganesh.compose.ui.theme.HelloComposeTheme
import dagger.hilt.android.AndroidEntryPoint

sealed class Screen(val screenName: String) {
    object LaborListing : Screen("LaborListing")
    object NewScreen : Screen("NewScreen")
    object EditScreen : Screen("EditScreen")
    object DetailScreen : Screen("DetailScreen")
    object LabourWorkListing : Screen("LabourWorkListing")
    object LabourWorkEntry : Screen("LabourWorkEntry")
}


@AndroidEntryPoint
class LabourActivity : ComponentActivity() {
    val peopleListingViewModel: PeopleListingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityContent(peopleListingViewModel)
        }
    }
}

@Composable
fun MainActivityContent(peopleListingViewModel: PeopleListingViewModel) {
    // You can mock the dependencies here if needed for preview
    // For example:
    // val fakePeopleListingViewModel = FakePeopleListingViewModel()
    // MainActivity(fakePeopleListingViewModel)
    // You can also pass other dependencies as needed

    // Call the original content function here
    HelloComposeTheme {
        val currentScreen = remember {
            mutableStateOf(Screen.LaborListing as Screen)
        }
        // A surface container using the 'background' color from the theme
        val navController = rememberNavController()

        val canPop = remember { mutableStateOf(false) }
//        Surface(color = MaterialTheme.colorScheme.background) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                hostPage(peopleListingViewModel, navController, canPop, currentScreen)
            }
//        }
    }
}

@Composable
fun FloatingActionButtonCompose(callback: () -> Unit) {
    FloatingActionButton(
        onClick = {
            callback()
        },
        containerColor = MaterialTheme.colorScheme.secondary,
        shape = RoundedCornerShape(16.dp),
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Add FAB",
            tint = Color.White,
        )
    }
}

@Composable
fun HomeScreen(
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
                        "Labor Listing",
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
        Column(Modifier.clickable {
            callback?.invoke(people.uid)
        }) {
            Text(
                text = "Name : " + people.firstName + " " + people.lastName,
                color = Color.Black,
                fontSize = TextUnit(14.0F, TextUnitType.Sp),
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.White)
                    .fillMaxWidth()
            )



            Text(
                text = "Age : " + " " + people.age,
                color = Color.Black,
                fontSize = TextUnit(14.0F, TextUnitType.Sp),
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.White)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainActivityPreview() {
//    MainActivityContent(peopleListingViewModel) // Replace with the actual composable function name used in MainActivity
    LaborItem(People("test", "testasfsdf", "testasfd", 23, "test"))
}


