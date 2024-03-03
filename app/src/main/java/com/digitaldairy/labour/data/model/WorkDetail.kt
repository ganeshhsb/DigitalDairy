package com.digitaldairy.labour.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "work_detail",
    foreignKeys = [ForeignKey(
        entity = People::class,
        parentColumns = ["uid"],
        childColumns = ["uid"]
    )],
    primaryKeys = ["uid","date"]
)
data class WorkDetail(
    @ColumnInfo(name = "uid") var uid: String,
    @ColumnInfo(name = "date") var date: Date,
    @ColumnInfo(name = "hours") var hours: Int,
    @ColumnInfo(name = "workDescription") var workDescription: String,
    @ColumnInfo(name = "isPaid") var isPaid: Boolean,
    @ColumnInfo(name = "dailyWage") var dailyWage: Int,
    @ColumnInfo(name = "amountPaid") var amountPaid: Int = 0
)