package com.ganesh.digitaldairy

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ganesh.digitaldairy.labour.ui.LaborDTO
import com.ganesh.digitaldairy.labour.ui.LabourDetailScreen
import com.ganesh.digitaldairy.labour.ui.LabourListing

@Composable
fun AppNavigationGraph(
    isExpandedScreen: Boolean,
    navigationActions: AppNavigationActions,
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    startDestination: String = AppDestination.LABOUR_LISTING
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AppDestination.LABOUR_LISTING) {
            val names = arrayListOf<LaborDTO>()

            repeat(100) { main ->
                names.add(LaborDTO("Person$main", 1, "Sagar"))
            }

            LabourListing(laborsList1 = names, navHostController)
        }
        composable(
            AppDestination.LABOUR_DETAIL + "/{name}",
            arguments = listOf(navArgument("name") { defaultValue = "Ganesh" })
        ) { backStackEntry ->
            LabourDetailScreen(backStackEntry.arguments?.getString("name"))
        }
    }
}