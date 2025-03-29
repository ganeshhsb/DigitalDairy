package com.digitaldairy.labour

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.digitaldairy.labour.data.AppDatabase
import com.digitaldairy.labour.data.dao.PersonDao
import com.digitaldairy.labour.data.model.Person
import com.digitaldairy.labour.data.model.SexType
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomDbBenchmarkTest {

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
        benchmarkRule.measureRepeated {
            runTest {
                // Insert a sample user
                personDao.insert(
                    Person("Nagesh", "shetty", 40, "Phone number", SexType.MALE, "Address")
                )
            }
        }
}