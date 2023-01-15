package com.ganesh.digitaldairy

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

object AppDestination {
    const val LABOUR_LISTING = "labour_listing"
    const val LABOUR_DETAIL = "labour_detail"
}

class AppNavigationActions(navController: NavController){
    val labourListing: () -> Unit = {
        navController.navigate(AppDestination.LABOUR_LISTING){
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val labourDetail: () -> Unit = {
        navController.navigate(AppDestination.LABOUR_DETAIL){
            popUpTo(navController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}