package com.digitaldairy.labour.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people")
data class People (
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "first_name") var firstName:String,
    @ColumnInfo(name = "last_name") var lastName:String,
    @ColumnInfo(name = "age") var age:Int,
    @ColumnInfo(name = "address") var address:String
)