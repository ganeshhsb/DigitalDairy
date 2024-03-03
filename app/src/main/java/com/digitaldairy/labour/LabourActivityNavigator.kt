package com.digitaldairy.labour

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.digitaldairy.labour.workscreen.LabourWorkEntry
import com.digitaldairy.labour.workscreen.LabourWorkList
import com.ganesh.compose.listing.LoadingScreen
import com.ganesh.compose.listing.PeopleDetailScreen
import com.ganesh.compose.listing.PeopleEntryScreen
import com.ganesh.compose.listing.PeopleListingViewModel

@Composable
fun hostPage(
    peopleListingViewModel: PeopleListingViewModel = hiltViewModel(),
    navController: NavHostController,
    canPop: MutableState<Boolean>,
    currentScreen: MutableState<Screen>
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController) { userId ->
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
            route = "${Screen.LabourWorkListing.screenName}/{userId}",
        ) { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getString("userId", "") ?: ""
            LabourWorkList(
                navController = navController, userId = userId
            )
        }

        composable(
            route = "${Screen.LabourWorkEntry.screenName}/{userId}",
            arguments = listOf(navArgument("userId") {
                type = NavType.StringType
                defaultValue = ""
                nullable = false
            })
        ) { navBackStackEntry ->
            val userId = navBackStackEntry.arguments?.getString("userId", "") ?: ""
            LabourWorkEntry(
                navController = navController,
                userId = userId
            )
        }
        // endregion
    }
}