package com.ganesh.compose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.digitaldairy.labour.data.DateConverter
import com.digitaldairy.labour.data.dao.AddressDao
import com.digitaldairy.labour.data.dao.WorkDetailDao
import com.digitaldairy.labour.data.model.Address
import com.ganesh.compose.data.dao.PeopleDao
import com.digitaldairy.labour.data.model.People
import com.digitaldairy.labour.data.model.WorkDetail

@Database(entities = [People::class, WorkDetail::class, Address::class], version = 1)
@TypeConverters(value = [DateConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun peopleDao(): PeopleDao
    abstract fun workDetailDao(): WorkDetailDao
    abstract fun addressDao(): AddressDao
}