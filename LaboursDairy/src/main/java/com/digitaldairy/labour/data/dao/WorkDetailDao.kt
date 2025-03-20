package com.digitaldairy.labour.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.digitaldairy.labour.data.model.PersonWithWorkDetail
import com.digitaldairy.labour.data.model.WorkDetail
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface WorkDetailDao {
//    @Query("SELECT * FROM work_detail")
//    suspend fun getAll(): List<WorkDetail>

    @Query("SELECT * FROM person  WHERE uid = :userId")
    fun getAllAsLiveData(userId: String): Flow<PersonWithWorkDetail>

//    @Query("SELECT * FROM work_detail WHERE uid IN (:userIds)")
//    suspend fun loadAllByIds(userIds: StringAr): LiveData<List<WorkDetail>>

//    @Query("SELECT * FROM person WHERE uid = :userId")
//    fun loadAllById(userId: String): Flow<List<WorkDetail>>

    @Query("SELECT * FROM person WHERE uid = :userId")
    fun getWorkDetail(userId: String): PersonWithWorkDetail

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg workDetail: WorkDetail)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg workDetail: WorkDetail)

    @Delete
    suspend fun delete(workDetail: WorkDetail)
}