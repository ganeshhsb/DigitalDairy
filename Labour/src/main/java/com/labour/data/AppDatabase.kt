package com.ganesh.compose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ganesh.compose.data.dao.PeopleDao
import com.ganesh.compose.data.model.People

@Database(entities = [People::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun peopleDao(): PeopleDao
}