package com.digitaldairy.labour.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.digitaldairy.labour.data.model.DailyWork
import com.digitaldairy.labour.data.model.PersonWithWorkDetail
import com.digitaldairy.labour.data.model.WorkAndCategoryCrossRef
import com.digitaldairy.labour.data.model.WorkCategory
import com.digitaldairy.labour.data.model.WorkDetail
import kotlinx.coroutines.flow.Flow
import java.util.Date
import java.util.UUID

@Dao
interface WorkDetailDao {
    @Transaction
    @Query("SELECT * FROM person  WHERE person_id = :userId")
    fun getAllAsLiveData(userId: String): Flow<PersonWithWorkDetail>

    @Transaction
    @Query("SELECT * FROM person WHERE person_id = :userId")
    fun getWorkDetailList(userId: UUID): List<PersonWithWorkDetail>

    @Transaction
    @Query("SELECT * FROM person WHERE person_id = :userId")
    fun getWorkDetail(userId: UUID): PersonWithWorkDetail

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg workDetail: DailyWork):List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg workDetail: DailyWork)

    @Delete
    suspend fun delete(workDetail: DailyWork)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(workDetail: WorkCategory):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(workAndCategory: WorkAndCategoryCrossRef):Long
}