package com.digitaldairy.labour.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.digitaldairy.labour.data.model.Person
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface PersonDao {
    @Query("SELECT * FROM person")
    suspend fun getAll(): List<Person>

    @Query("SELECT * FROM person")
    fun getAllAsLiveData(): Flow<List<Person>>

    @Query("SELECT * FROM person WHERE person_id IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<Person>

    @Query(
        "SELECT * FROM person WHERE first_name LIKE :first AND " +
                "last_name LIKE :last LIMIT 1"
    )
    suspend fun findByName(first: String, last: String): Person

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: Person): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg person: Person): List<Long>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAbort(vararg person: Person)

    @Update
    suspend fun update(vararg person: Person)

    @Delete
    suspend fun delete(user: Person)

    @Query("SELECT COUNT(*) FROM person")
    fun getPersonCount(): Int

    @Query("SELECT * FROM person WHERE person_id = :personId")
    fun getPerson(personId: UUID):Person?
}