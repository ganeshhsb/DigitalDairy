package com.digitaldairy.labour.data.model

import androidx.room.ColumnInfo
import java.util.Date
import java.util.UUID

abstract class WorkDetail(
    @ColumnInfo(name = "person_id") var personId: UUID,
    @ColumnInfo(name = "date") var date: Date,
    @ColumnInfo(name = "hours") var hours: Int,
    @ColumnInfo(name = "workDescription") var workDescription: String,
    @ColumnInfo(name = "amountPaid") var amountPaid: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is WorkDetail) return false

        if (personId != other.personId) return false
        if (date != other.date) return false
        if (hours != other.hours) return false
        if (workDescription != other.workDescription) return false
        return amountPaid == other.amountPaid
    }

    override fun hashCode(): Int {
        var result = personId.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + hours
        result = 31 * result + workDescription.hashCode()

        result = 31 * result + amountPaid
        return result
    }
}