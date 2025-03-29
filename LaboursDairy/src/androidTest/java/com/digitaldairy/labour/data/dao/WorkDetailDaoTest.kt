package com.digitaldairy.labour.data.dao

import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.digitaldairy.labour.data.AppDatabase
import com.digitaldairy.labour.data.model.DailyWork
import com.digitaldairy.labour.data.model.Person
import com.digitaldairy.labour.data.model.SexType
import com.digitaldairy.labour.data.model.WorkAndCategoryCrossRef
import com.digitaldairy.labour.data.model.WorkCategory
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Calendar
import java.util.Date
import java.util.UUID

private const val uid = "uid"

private const val firstName = "first_name"

private const val lastName = "last_name"

private const val age = 39

private const val phoneNumber = "8970103305"

private val sex = SexType.MALE

private const val address = "holekoppa"

// <unitOfWork>_<stateUnderTest>_<expectedResult>
class WorkDetailDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var personDao: PersonDao
    private lateinit var workDetailDao: WorkDetailDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        personDao = database.personDao()
        workDetailDao = database.workDetailDao()
    }

    @Test
    fun insertDailyWork_insertDailyWork_success() = runTest {
        val person = getPerson()
        val dailyWork = getDailyWork(person)
        personDao.insert(person)
        workDetailDao.insert(dailyWork)
    }


    @Test
    fun insertDailyWork_getDailyWork_success() = runTest {
        val person = getPerson()
        val dailyWork = getDailyWork(person)
        personDao.insert(person)
        workDetailDao.insert(dailyWork)

        val category = getWorkCategory()
        workDetailDao.insert(category)

        workDetailDao.insert(getWorkAndCategory(dailyWork, category))

        val workDetail = workDetailDao.getWorkDetail(person.personId)
        assertEquals(
            dailyWork.workDescription,
            workDetail.workDetailList[0].dailyWork.workDescription
        )
    }

    @Test
    fun insertMultipleDailyWork_getDailyWork_success() = runTest {
        val person = getPerson()
        val daily1Work = getDailyWork(person, Date())
        val calendar = Calendar.getInstance()
        calendar.set(2025, 3, 31)
        val daily2Work = getDailyWork(person, calendar.time)
        personDao.insert(person)
        workDetailDao.insert(daily1Work)
        workDetailDao.insert(daily2Work)

        val category = getWorkCategory()
        workDetailDao.insert(category)

        workDetailDao.insert(getWorkAndCategory(daily1Work, category))
        workDetailDao.insert(getWorkAndCategory(daily2Work, category))

        val workDetail = workDetailDao.getWorkDetail(person.personId)
        assertEquals(
            daily1Work.workDescription,
            workDetail.workDetailList[0].dailyWork.workDescription
        )
        assertEquals(
            daily2Work.workDescription,
            workDetail.workDetailList[1].dailyWork.workDescription
        )
    }


    @Test
    fun insertMultipleDailyWorkWithCategory_getDailyWork_success() = runTest {
        val category = getWorkCategory()
//        val workAndCategory = getWorkAndCategory()
        val person = getPerson()
        val daily1Work = getDailyWork(person, Date())

        val calendar = Calendar.getInstance()
        calendar.set(2025, 3, 31)
        val daily2Work = getDailyWork(person, calendar.time)

        personDao.insert(person)

        workDetailDao.insert(daily1Work)
        workDetailDao.insert(daily2Work)

        workDetailDao.insert(category)

        workDetailDao.insert(getWorkAndCategory(daily1Work, category))
        workDetailDao.insert(getWorkAndCategory(daily2Work, category))
        val workDetail = workDetailDao.getWorkDetail(person.personId)
        assertEquals(
            daily1Work.workDescription,
            workDetail.workDetailList[0].dailyWork.workDescription
        )
        assertEquals(
            daily2Work.workDescription,
            workDetail.workDetailList[1].dailyWork.workDescription
        )
    }

    private fun getWorkAndCategory(
        dailyWork: DailyWork,
        category: WorkCategory
    ): WorkAndCategoryCrossRef {
        return WorkAndCategoryCrossRef(
            categoryId = category.categoryId,
            date = dailyWork.date,
            personId = dailyWork.personId
        )
    }

    private fun getDailyWork(person: Person, date: Date = Date()): DailyWork {
        return DailyWork(person.personId, date, 6, "Day to day work", 500)
    }

    private fun getWorkCategory(): WorkCategory {
        return WorkCategory("categoryId", "CategoryName", "WorkDescription")
    }

    private fun getPerson(suffix: String = ""): Person {
        val person = Person(firstName + suffix, lastName + suffix, age, phoneNumber, sex, address)
        person.personId = person.generatePersonId()
        return person
    }
}