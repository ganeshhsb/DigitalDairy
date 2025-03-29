package com.ganesh.macrobenchmark

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.digitaldairy.labour.data.AppDatabase
import com.digitaldairy.labour.data.dao.PersonDao
import com.digitaldairy.labour.data.model.Person
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomDbBenchmarkTest {

//    @get:Rule(order = 1)
//    val activitycomposeTestRulestRule = createAndroidComposeRule<LabourActivity>()

    // Benchmark rule
    @get:Rule
    val benchmarkRule = BenchmarkRule()

    // Set up the database and DAO
    private lateinit var appDatabase: AppDatabase
    private lateinit var personDao: PersonDao

    @Before
    fun setup() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        personDao = appDatabase.personDao()
    }

    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun benchmarkRoomDbInsert() =
        benchmarkRule.measureRepeated(
//            packageName = "com.digitaldairy",
//            metrics = listOf(MemoryUsageMetric(Mode.Max)),
////            compilationMode = compilationMode,
//            startupMode = StartupMode.WARM,
//            iterations = 10
        ) {
            runBlocking {
                // Insert a sample user
                personDao.insert(
                    Person("Nagesh", "shetty", 40, "Phone number", "Male", "Address")
                )
            }
        }
}
//import androidx.benchmark.BenchmarkState
//import androidx.room.Room.inMemoryDatabaseBuilder
//import androidx.test.core.app.ApplicationProvider
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.digitaldairy.labour.data.AppDatabase
//import com.digitaldairy.labour.data.model.Person
//import com.google.firebase.firestore.auth.User
//import kotlinx.coroutines.test.runTest
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith

//@RunWith(AndroidJUnit4::class)
//class DatabaseBenchmark {
//    private var appDatabase: AppDatabase? = null
//
//    @Before
//    fun setUp() {
//        // Initialize Room Database instance
//        appDatabase = inMemoryDatabaseBuilder(
//            ApplicationProvider.getApplicationContext(),
//            AppDatabase::class.java
//        ).allowMainThreadQueries()
//            .build() // allowMainThreadQueries is for testing purposes, avoid in production
//    }
//
//    @Test
//    fun benchmarkInsert() = runTest {
//        val state = BenchmarkState()
//
//        while (state.keepRunning()) {
//            // Perform the insert operation in a loop for benchmarking
//            appDatabase?.personDao()?.insert(
//                Person("uid", "Nagesh", "shetty", 40, "Phone number", "Male", "Address")
//            )
//        }
//    }
//
//    @After
//    fun tearDown() {
//        appDatabase!!.close()
//    }
//}