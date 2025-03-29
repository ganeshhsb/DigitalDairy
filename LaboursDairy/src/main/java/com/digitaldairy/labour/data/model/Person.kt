package com.digitaldairy.labour.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

enum class SexType {
   MALE, FEMALE
}

@Entity(tableName = "person")
data class Person(

    @ColumnInfo(name = "first_name") var firstName: String,
    @ColumnInfo(name = "last_name") var lastName: String,
    @ColumnInfo(name = "age") var age: Int,
    @ColumnInfo(name = "phoneNumber") var phoneNumber: String,
    @ColumnInfo(name = "sex") var sex: SexType,
    @ColumnInfo(name = "address") var address: String = "",
    @ColumnInfo(name = "wagePerDay") var wagePerDay: Int = 0,
    @PrimaryKey
    @ColumnInfo(name = "person_id") var personId: UUID = UUID.randomUUID()
) {
    fun generatePersonId(): UUID {
        val compoundKey = firstName + " " + lastName + "__" + phoneNumber
        return UUID.nameUUIDFromBytes(compoundKey.toByteArray())
    }
}

@Entity(
    tableName = "labour_group",
    foreignKeys = [ForeignKey(
        entity = Person::class,
        parentColumns = ["person_id"],
        childColumns = ["group_id"]
    )]
)
data class LabourGroup(

    @Embedded
    val leader: Person,
    @ColumnInfo(name = "women_wage_per_day") val womenWagePerDay: Int,
    @ColumnInfo(name = "men_wage_per_day") val menWagePerDay: Int,
    @PrimaryKey
    @ColumnInfo(name = "group_id") val groupId: UUID = leader.personId,
)