package com.digitaldairy.labour

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.digitaldairy.labour.listing.PeopleDetailScreen
import com.digitaldairy.labour.listing.PeopleEntryScreen
import com.digitaldairy.labour.listing.PeopleListingScreen
import com.digitaldairy.labour.listing.PeopleListingViewModel
import com.digitaldairy.labour.workscreen.LabourWorkEntry
import com.digitaldairy.labour.workscreen.LabourWorkList
import java.util.Date

@Composable
fun hostPage(
    peopleListingViewModel: PeopleListingViewModel = hiltViewModel(),
    navController: NavHostController,
    canPop: MutableState<Boolean>,
    currentScreen: MutableState<Screen>
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            PeopleListingScreen(navController = navController) { userId ->
                currentScreen.value = Screen.DetailScreen
                canPop.value = true
                navController.navigate("${Screen.DetailScreen.screenName}/$userId")
            }
        }
        composable(Screen.NewScreen.screenName) { navBackStackEntry ->
            currentScreen.value = Screen.NewScreen
            PeopleEntryScreen(
                peopleListingViewModel = peopleListingViewModel,
                navController = navController
            )
        }
        composable(
            route = "${Screen.EditScreen.screenName}/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.StringType
                defaultValue = ""
                nullable = false
            })
        ) { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getString("userId", "") ?: ""
            currentScreen.value = Screen.NewScreen
            PeopleEntryScreen(peopleListingViewModel = peopleListingViewModel,
                navController = navController,
                people = peopleListingViewModel.peopleListLiveData.value?.firstOrNull { it.uid == userId }
            )
        }
        composable(
            route = "${Screen.DetailScreen.screenName}/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.StringType
                defaultValue = ""
                nullable = false
            })
        ) { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getString("userId", "") ?: ""
            PeopleDetailScreen(
                peopleListingViewModel = peopleListingViewModel,
                navController = navController,
                userId = userId
            )
//            LoadingScreen()
        }

        // region work entry
        composable(
            route = "${Screen.LabourWorkListing.screenName}/{userId}?date={date}"
        ) { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getString("userId", "") ?: ""
            LabourWorkList(
                navController = navController, userId = userId
            )
        }

        composable(
            route = "${Screen.LabourWorkEntry.screenName}/{userId}?date={date}",
            arguments = listOf(navArgument("userId") {
                type = NavType.StringType
                defaultValue = ""
                nullable = false
            },
                navArgument("date") {
                    type = NavType.LongType
                    defaultValue = -1L
                    nullable = false
                })
        ) { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getString("userId", "") ?: ""
            val dateInMillis = navBackStackEntry.arguments?.getLong("date") ?: -1L
            val date = if (dateInMillis != -1L) {
                Date(dateInMillis)
            } else {
                null
            }
            LabourWorkEntry(
                navController = navController,
                userId = userId, date = date
            )
        }
        // endregion
    }
}