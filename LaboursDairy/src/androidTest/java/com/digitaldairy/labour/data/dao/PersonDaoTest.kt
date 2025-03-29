package com.digitaldairy.labour.data.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.digitaldairy.labour.data.AppDatabase
import com.digitaldairy.labour.data.model.Person
import com.digitaldairy.labour.data.model.SexType
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

private const val uid = "uid"

private const val firstName = "first_name"
private const val lastName = "last_name"
private const val age = 39
private const val phoneNumber = "8970103305"
private val sex = SexType.MALE
private const val address = "holekoppa"

// <unitOfWork>_<stateUnderTest>_<expectedResult>
@RunWith(AndroidJUnit4::class)
class PersonDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var personDao: PersonDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        personDao = database.personDao()

    }

    @Test
    fun insertPerson_should_insert_person() = runTest {
        personDao.insert(
            Person(firstName, lastName, age, phoneNumber, sex, address)
        )
        val persons = personDao.getAll()
        assert(persons.size == 1)
    }

    @Test
    fun insertMultiplePersons_should_insert_all_persons() = runTest {
        personDao.insertPerson(getPerson("first"))
        personDao.insertPerson(getPerson("second"))
        val personCount = personDao.getPersonCount()
        assertEquals(2, personCount)
    }

    @Test
    fun insertDuplicatePerson_should_fail() = runTest {
        val firstInsert = personDao.insertPerson(getPerson())
        val secondInsert = personDao.insertPerson(getPerson())
        assertEquals(firstInsert + 1, secondInsert)
        val personCount = personDao.getPersonCount()
        assertEquals(1, personCount)
    }

    @Test
    fun getPerson_success_returnedInsertedPerson() = runTest {
        val person = getPerson()
        personDao.insert(person)
        val personFromDB = personDao.getPerson(person.personId)
        assertEquals(person.personId, personFromDB?.personId)
    }

    @Test
    fun deletePerson_success_deletedPerson() = runTest {
        val person = getPerson()
        personDao.delete(person)
        val personFromDB = personDao.getPerson(person.personId)
        assertNull(personFromDB)
    }

    @After
    fun teaDown() {

    }

    private fun getPerson(suffix: String = ""): Person {
        val person = Person(firstName + suffix, lastName + suffix, age, phoneNumber, sex, address)
        person.personId = person.generatePersonId()
        return person
    }
}