package com.digitaldairy.labour.data.model

import androidx.room.ColumnInfo

data class GroupWorkDetails(
    @ColumnInfo(name = "") var numberOfMaleWorker: Int,
    @ColumnInfo(name = "") var numberOfFemaleWorker: Int,
    @ColumnInfo(name = "") var maleWorkersWage: Int,
    @ColumnInfo(name = "") var femaleWorkersWage: Int
)