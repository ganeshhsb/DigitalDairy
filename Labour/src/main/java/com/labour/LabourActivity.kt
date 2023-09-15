package com.labour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ganesh.compose.data.model.People
import com.ganesh.compose.listing.PeopleListingViewModel
import com.ganesh.compose.ui.theme.HelloComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class LabourActivity : ComponentActivity() {
    //    @Inject
    sealed class Screen() {
        class LaborListing : Screen()
        class NewScreen : Screen()
        class EditScreen : Screen()
        class DetailScreen : Screen()
    }

    val peopleListingViewModel: PeopleListingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        peopleListingViewModel.insertPeople(People(UUID.randomUUID().toString(), "Ganesh123", "HS"))
        setContent {
            MainActivityContent()
        }
    }

    @Composable
    fun MainActivityContent() {
        // You can mock the dependencies here if needed for preview
        // For example:
        // val fakePeopleListingViewModel = FakePeopleListingViewModel()
        // MainActivity(fakePeopleListingViewModel)
        // You can also pass other dependencies as needed

        // Call the original content function here
        HelloComposeTheme {
            val currentScreen = remember {
                mutableStateOf(Screen.LaborListing() as Screen)
            }
            // A surface container using the 'background' color from the theme
            val navController = rememberNavController()

            var canPop = remember { mutableStateOf(false) }
            Surface(color = MaterialTheme.colorScheme.background) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    hostPage(navController, canPop, currentScreen)
                }
            }
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
    private fun hostPage(
        navController: NavHostController,
        canPop: MutableState<Boolean>,
        currentScreen: MutableState<Screen>
    ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(navController) { userId ->
                    currentScreen.value = Screen.DetailScreen()
                    canPop.value = true
                    navController.navigate("detail/$userId")
                }
            }
            composable("newScreen") { navBackStackEntry ->
                currentScreen.value = Screen.NewScreen()
                NewScreen(navController)
            }
            composable(
                route = "editScreen/{userId}",
                arguments = listOf(navArgument("userId") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                })
            ) { navBackStackEntry ->
                val userId = navBackStackEntry.arguments?.getString("userId", "") ?: ""
                currentScreen.value = Screen.NewScreen()
                NewScreen(
                    navController,
                    peopleListingViewModel.peopleListLiveData.value?.firstOrNull { it.uid == userId }
                )
            }
            composable(
                route = "detail/{userId}",
                arguments = listOf(navArgument("userId") {
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                })
            ) { navBackStackEntry ->
                val userId = navBackStackEntry.arguments?.getString("userId", "") ?: ""
                DetailScreen(
                    navController,
                    userId
                )
            }
        }
    }

    @Composable
    fun HomeScreen(navController: NavHostController, callback: (userId: String) -> Unit) {
        ScreenTopLayout(
            screen = Screen.LaborListing(),
            topBar = {
                AppToolbar(
                    "Labor Listing",
                    Screen.LaborListing(),
                    navController = navController
                )
            },
            navController = navController,
            showFloatingActionButton = true,
            { navController.navigate("newScreen") }
        ) {
            val state = peopleListingViewModel.getAllPeople().observeAsState()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                LazyColumn {
                    state.value?.forEach {
                        item {
                            Text(
                                text = it.firstName,
                                color = MaterialTheme.colorScheme.onTertiary,
                                fontSize = TextUnit(14.0F, TextUnitType.Sp),
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        callback.invoke(it.uid)
                                    })
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ScreenTopLayout(
        screen: Screen,
        topBar: @Composable () -> Unit,
        navController: NavHostController,
        showFloatingActionButton: Boolean = false,
        floatingActionButtonCallback: (() -> Unit)? = null,
        content: @Composable () -> Unit

    ) {
        Scaffold(
            topBar = topBar,
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    content.invoke()
                }
            },

            floatingActionButton = {
                if (showFloatingActionButton) {
                    FloatingActionButtonCompose {
                        floatingActionButtonCallback?.invoke()
                    }
                }
            }

        )
    }

    @Composable
    private fun NewScreen(navController: NavHostController, people: People? = null) {
        val peopleState: MutableState<People>
        val screen: Screen
        var title = ""
        if (people != null) {
            peopleState = remember { mutableStateOf(people) }
            screen = Screen.EditScreen()
            title = "Edit labor details"
        } else {
            peopleState = remember { mutableStateOf(People(UUID.randomUUID().toString(), "", "")) }
            screen = Screen.NewScreen()
            title = "Create new labor"
        }


        ScreenTopLayout(
            screen = screen,
            topBar = {
                AppToolbar(
                    title = title,
                    currentScreen = screen,
                    navController = navController,
                    onDoneClick = {
                        peopleListingViewModel.saveData(peopleState.value) {
                            navController.popBackStack()
                        }
                    },
                    onCancelClick = {
                        navController.popBackStack()
                    }
                )
            },
            navController = navController
        ) {
            Column {
                Row {
                    Text(
                        text = "First Name : ", color = MaterialTheme.colorScheme.onTertiary,
                        fontSize = TextUnit(14.0F, TextUnitType.Sp)
                    )
                    TextField(peopleState.value.firstName, onValueChange = {
                        peopleState.value =
                            People(peopleState.value.uid, it, peopleState.value.lastName)
                    }, textStyle = TextStyle(color = Color.Black))
                }
                Row {
                    Text(
                        text = "Last Name : ", color = MaterialTheme.colorScheme.onTertiary,
                        fontSize = TextUnit(14.0F, TextUnitType.Sp)
                    )
                    TextField(peopleState.value.lastName, onValueChange = {
                        peopleState.value =
                            People(peopleState.value.uid, peopleState.value.firstName, it)
                    }, textStyle = TextStyle(color = Color.Black))
                }
            }
        }
    }

    @Composable
    private fun DetailScreen(
        navController: NavHostController,
        userId: String
    ) {
        val personState = peopleListingViewModel.getAllPeople().observeAsState()
        val people = personState.value?.first { it.uid == userId }!!
        ScreenTopLayout(
            screen = Screen.DetailScreen(),
            topBar = {
                AppToolbar(
                    title = "Labor detail",
                    currentScreen = Screen.DetailScreen(),
                    navController = navController,
                    onEditClick = {
                        navController.navigate("editScreen/${people.uid}")
                    }
                )
            },
            navController = navController
        )
        {
            Column {
                Text(
                    text = "Name", color = MaterialTheme.colorScheme.onTertiary,
                    fontSize = TextUnit(14.0F, TextUnitType.Sp)
                )
                Text(
                    text = people.firstName + " " + people.lastName,
                    color = MaterialTheme.colorScheme.onTertiary,
                    fontSize = TextUnit(14.0F, TextUnitType.Sp)
                )
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppToolbar(
        title: String,
        currentScreen: Screen,
        navController: NavHostController,
        onDoneClick: (() -> Unit)? = null,
        onCancelClick: (() -> Unit)? = null,
        onBackClick: (() -> Unit)? = null,
        onEditClick: (() -> Unit)? = null
    ) {
        val showMenu = currentScreen is Screen.LaborListing
        val canEdit = currentScreen is Screen.EditScreen || currentScreen is Screen.NewScreen
        val showEditIcon = currentScreen is Screen.DetailScreen
        TopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                if (canEdit) {
                    ToolbarButton(Icons.Filled.Close) { onCancelClick?.invoke() }
                } else if (showMenu) {
                    ToolbarButton(Icons.Filled.Menu) { navController.popBackStack() }
                } else {
                    ToolbarButton(Icons.Filled.ArrowBack) { navController.popBackStack() }
                }
            },
            actions = {
                if (canEdit)
                    ToolbarButton(Icons.Filled.Done) { onDoneClick?.invoke() }
                else if (showEditIcon) {
                    ToolbarButton(Icons.Filled.Edit) { onEditClick?.invoke() }
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }

    @Composable
    fun ToolbarButton(imageVector: ImageVector, callback: () -> Unit) {
        IconButton(onClick = callback) {
            Icon(imageVector = imageVector, contentDescription = "")
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainActivityPreview() {
        MainActivityContent() // Replace with the actual composable function name used in MainActivity
    }


}