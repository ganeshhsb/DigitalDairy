package com.digitaldairy.labour.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.digitaldairy.labour.data.dao.AddressDao
import com.digitaldairy.labour.data.dao.PersonDao
import com.digitaldairy.labour.data.dao.WorkDetailDao
import com.digitaldairy.labour.data.model.Address
import com.digitaldairy.labour.data.model.Person
import com.digitaldairy.labour.data.model.WorkCategory
import com.digitaldairy.labour.data.model.WorkDetail

@Database(entities = [Person::class, WorkDetail::class, Address::class,WorkCategory::class], version = 3)
@TypeConverters(value = [DateConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun workDetailDao(): WorkDetailDao
    abstract fun addressDao(): AddressDao
}
