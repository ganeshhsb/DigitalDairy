package com.digitaldairy.labour.listing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.digitaldairy.labour.ILabourRepository
import com.digitaldairy.labour.data.model.Person
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PersonListingViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher) // ✅ Controlled test scope
    private lateinit var repository: ILabourRepository
    private lateinit var viewModel: PersonListingViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk<ILabourRepository>()
        every { repository.getAllPersonsDataAsFlow() } returns flowOf(
            listOf(Person("uid", "Nagesh", "Shetty", 40, "Phone", "Male", "Address"))
        )
        viewModel = PersonListingViewModel(mockk(), repository,testScope)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `personFlow should emit persons list from repository`() = runTest {
        advanceUntilIdle()
        viewModel.personFlow.test {
            val event = awaitEvent()
            val emission = awaitItem()
            assertEquals(1, emission.size)
            assertEquals("Nagesh", emission[0].firstName)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `saveData should insert person into repository`() = runTest {
        val person = Person("uid2", "John", "Doe", 35, "1234567890", "Male", "Some Address")
        coEvery { repository.insertPerson(person) } just Runs

        viewModel.saveData(person) {}
        advanceUntilIdle()

        coVerify(exactly = 1) { repository.insertPerson(person) }
    }
}