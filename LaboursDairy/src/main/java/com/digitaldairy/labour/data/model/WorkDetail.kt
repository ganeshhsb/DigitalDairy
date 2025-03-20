package com.digitaldairy.labour.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Relation
import java.util.Date

@Entity(
    tableName = "work_detail",
    foreignKeys = [ForeignKey(
        entity = Person::class,
        parentColumns = ["uid"],
        childColumns = ["userCreatorId"]
    )],
    primaryKeys = ["userCreatorId","date"]
)
data class WorkDetail(
    @ColumnInfo(name = "userCreatorId") var uid: String,
    @ColumnInfo(name = "date") var date: Date,
    @ColumnInfo(name = "hours") var hours: Int,
    @ColumnInfo(name = "workDescription") var workDescription: String,
    @ColumnInfo(name = "isPaid") var isPaid: Boolean,
    @ColumnInfo(name = "dailyWage") var dailyWage: Int,
    @ColumnInfo(name = "amountPaid") var amountPaid: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is WorkDetail) return false

        if (uid != other.uid) return false
        if (date != other.date) return false
        if (hours != other.hours) return false
        if (workDescription != other.workDescription) return false
        if (isPaid != other.isPaid) return false
        if (dailyWage != other.dailyWage) return false
        return amountPaid == other.amountPaid
    }

    override fun hashCode(): Int {
        var result = uid.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + hours
        result = 31 * result + workDescription.hashCode()
        result = 31 * result + isPaid.hashCode()
        result = 31 * result + dailyWage
        result = 31 * result + amountPaid
        return result
    }
}

data class PersonWithWorkDetail(
    @Embedded val person: Person,
    @Relation(
        parentColumn = "uid",
        entityColumn = "userCreatorId"
    )
    val workDetailList: List<WorkDetail>
)