//package com.digitaldairy.labour
//
//import androidx.compose.ui.test.assertIsDisplayed
//import androidx.compose.ui.test.hasTestTag
//import androidx.compose.ui.test.junit4.createAndroidComposeRule
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.onNodeWithText
//import androidx.compose.ui.test.onRoot
//import androidx.compose.ui.test.performClick
//import androidx.compose.ui.test.printToLog
//import androidx.navigation.NavHostController
//import androidx.navigation.testing.TestNavHostController
//import androidx.test.core.app.ApplicationProvider
//import com.digitaldairy.compose.appcomponents.theme.DigitalDairyTheme
//import com.digitaldairy.labour.data.model.Person
//import com.digitaldairy.labour.listing.PersonListingScreen
//import junit.framework.TestCase.assertEquals
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//
//class PersonListingScreenTest {
//
//    @get:Rule(order = 1)
//    val activitycomposeTestRule = createAndroidComposeRule<LabourActivity>()
//
//
//    @get:Rule(order = 2)
//    val composeTestRule = createComposeRule()
//
//    private lateinit var mockViewModel: FakePeopleListViewModel
//    private lateinit var navController: NavHostController
//    private var clickedUserId: String? = null
//
//    @Before
//    fun setUp() {
//        mockViewModel = FakePeopleListViewModel()
//        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
//
//        composeTestRule.setContent {
//            DigitalDairyTheme {
//                PersonListingScreen(
//                    personListingViewModel = mockViewModel,
//                    navController = navController
//                ) { userId ->
//                    clickedUserId = userId
//                }
//            }
//        }
//    }
//
//    @Test
//    fun testLoadingState_ShowsCircularIndicator_WhenListIsEmpty() {
//        // Ensure the list is empty
//        mockViewModel.setPeople(emptyList())
//
//        composeTestRule.onNode(hasTestTag("loading_indicator")).assertIsDisplayed()
//    }
//
//    @Test
//    fun testPersonList_Displayed_WhenDataAvailable() {
//        val testPeople = listOf(
//            Person(
//                uid = "1",
//                firstName = "John",
//                lastName = "Doe",
//                age = 30,
//                phoneNumber = "",
//                address = "",
//                sex = ""
//            ),
//            Person(
//                uid = "2",
//                firstName = "Jane",
//                lastName = "Doe",
//                age = 28,
//                phoneNumber = "",
//                address = "",
//                sex = ""
//            )
//        )
//
//        mockViewModel.setPeople(testPeople)
//        activitycomposeTestRule.waitForIdle()
//        composeTestRule.onRoot().printToLog("TEST_TAG")
//
//        // Check if names are displayed
//        composeTestRule.onNodeWithText("John",true,true).assertIsDisplayed()
//        composeTestRule.onNodeWithText("Jane",true,true).assertIsDisplayed()
//    }
//
//    @Test
//    fun testClickingLaborItem_TriggersCallback() {
//        val testPerson = Person(
//            uid = "1",
//            firstName = "John",
//            lastName = "Doe",
//            age = 30,
//            phoneNumber = "",
//            address = "",
//            sex = ""
//        )
//        mockViewModel.setPeople(listOf(testPerson))
//
//        // Click on the first item
//        composeTestRule.onNodeWithText("John Doe").performClick()
//
//        // Check if callback was triggered
//        assertEquals("1", clickedUserId)
//    }
//}