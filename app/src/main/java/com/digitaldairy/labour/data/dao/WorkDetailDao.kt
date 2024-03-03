package com.digitaldairy.labour.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.digitaldairy.labour.data.model.People
import com.digitaldairy.labour.data.model.WorkDetail

@Dao
interface WorkDetailDao {
//    @Query("SELECT * FROM work_detail")
//    suspend fun getAll(): List<WorkDetail>

    @Query("SELECT * FROM work_detail")
    fun getAllAsLiveData(): LiveData<List<WorkDetail>>

//    @Query("SELECT * FROM work_detail WHERE uid IN (:userIds)")
//    suspend fun loadAllByIds(userIds: StringAr): LiveData<List<WorkDetail>>

    @Query("SELECT * FROM work_detail WHERE uid = :userId")
    fun loadAllById(userId: String): LiveData<List<WorkDetail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg workDetail: WorkDetail)

    @Update
    suspend fun update(vararg workDetail: WorkDetail)

    @Delete
    suspend fun delete(workDetail: WorkDetail)
}