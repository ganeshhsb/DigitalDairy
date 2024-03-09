package com.digitaldairy.labour.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.digitaldairy.labour.data.model.Address

@Dao
interface AddressDao {
    @Insert
    suspend fun insert(address: Address)

    @Update
    suspend fun update(address: Address)

    @Delete
    suspend fun delete(address: Address)

    @Query("SELECT * FROM address")
    suspend fun getAllAddresses(): List<Address>
}