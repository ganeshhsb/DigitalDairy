package com.digitaldairy.labour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.digitaldairy.labour.data.model.People
import com.digitaldairy.labour.listing.LaborItem
import com.digitaldairy.labour.listing.PeopleListingViewModel
import com.digitaldairy.labour.theme.HelloComposeTheme
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


@Preview(showSystemUi = true)
@Composable
fun MainActivityPreview() {
//    MainActivityContent(peopleListingViewModel) // Replace with the actual composable function name used in MainActivity
    LaborItem(People("test", "testasfsdf", "testasfd", 23, "test"))
}


