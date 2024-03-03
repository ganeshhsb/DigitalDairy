package com.ganesh.compose.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.digitaldairy.labour.data.model.People

@Dao
interface PeopleDao {
    @Query("SELECT * FROM people")
    suspend fun getAll(): List<People>

    @Query("SELECT * FROM people")
    fun getAllAsLiveData(): LiveData<List<People>>

    @Query("SELECT * FROM people WHERE uid IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<People>

    @Query("SELECT * FROM people WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    suspend fun findByName(first: String, last: String): People

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg people: People)

    @Update
    suspend fun update(vararg people: People)

    @Delete
    suspend fun delete(user: People)
}