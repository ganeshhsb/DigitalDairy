package com.digitaldairy.labour

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.digitaldairy.labour.data.model.Person
import com.digitaldairy.labour.data.model.PersonWithWorkDetail
import com.digitaldairy.labour.data.model.WorkDetail
import com.digitaldairy.labour.repo.LabourRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class LabourRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var roomDataStore: RoomDataStore
    private lateinit var firebaseDataStore: FirebaseDataStore
    private lateinit var repository: LabourRepository

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        roomDataStore = mockk()
        firebaseDataStore = mockk(relaxed = true) // Ignore Firebase for now

        repository = LabourRepository(roomDataStore, firebaseDataStore)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getAllPersonsDataAsFlow should emit list of persons`() = runTest {
        // Arrange: Mock Room DB flow
        val expectedPersons = listOf(
            Person("1", "John", "Doe", 30, "1234567890", "Male", "Some Address"),
            Person("2", "Jane", "Doe", 28, "0987654321", "Female", "Another Address")
        )

        every { roomDataStore.getAllPersonsAsWorkflow() } returns flowOf(expectedPersons)

        // Act & Assert: Collect emissions using Turbine
        repository.getAllPersonsDataAsFlow().test {
            assertEquals(awaitItem(), expectedPersons)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `insertPerson should insert data into RoomDataStore`() = runTest {
        // Arrange
        val person = Person("3", "Alice", "Smith", 25, "1112223333", "Female", "Somewhere")
        coEvery { roomDataStore.insertPerson(person) } just Runs

        // Act
        repository.insertPerson(person)

        // Assert
        coVerify { roomDataStore.insertPerson(person) }
    }

    @Test
    fun `insertWorkDetail should insert work detail into RoomDataStore`() = runTest {
        // Arrange
        val workDetail = WorkDetail("1",Date(), 6,"Harvesting", false,500)
        coEvery { roomDataStore.insertWorkDetail(workDetail) } just Runs

        // Act
        repository.insertWorkDetail("1", workDetail)

        // Assert
        coVerify { roomDataStore.insertWorkDetail(workDetail) }
    }

    @Test
    fun `getAllWorkDetailsFor should return work details as Flow`() = runTest {
        // Arrange
        val expectedWorkDetail = PersonWithWorkDetail(
            person = Person("1", "John", "Doe", 30, "1234567890", "Male", "Some Address"),
            workDetailList = listOf(WorkDetail("1",Date(), 6,"Harvesting", false,500))
        )

        every { roomDataStore.getAllWorkDetailsFor("1") } returns flowOf(expectedWorkDetail)

        // Act & Assert
        repository.getAllWorkDetailsFor("1").test {
            assertEquals(awaitItem(), expectedWorkDetail)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getWorkInfo should return work detail for a specific date`() = runTest {
        // Arrange
        val date = Date()
        val expectedWorkDetail = PersonWithWorkDetail(
            person = Person("1", "John", "Doe", 30, "1234567890", "Male", "Some Address"),
            workDetailList = listOf(WorkDetail("1",Date(), 6,"Harvesting", false,500))
        )

        every { roomDataStore.getWorkInfo("1", date) } returns expectedWorkDetail

        // Act
        val result = repository.getWorkInfo("1", date)

        // Assert
        assertEquals(expectedWorkDetail, result)
    }
}
