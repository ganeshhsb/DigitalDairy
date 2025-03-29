package com.digitaldairy.labour.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date
import java.util.UUID

@Entity(
    tableName = "work_category",
)
data class WorkCategory(
    //@PrimaryKey(autoGenerate = true) val id: Int = 0,
    @PrimaryKey
    @ColumnInfo(name = "category_id") val categoryId: String, // Should match WorkDetail’s primary key structure
    @ColumnInfo(name = "category_name") val categoryName: String,
    @ColumnInfo(name = "description") val description: String,
)

@Entity(
    foreignKeys = [
        ForeignKey(DailyWork::class, ["person_id", "date"], ["person_id","date"]),
        ForeignKey(WorkCategory::class, ["category_id"], ["category_id"])
    ],
    primaryKeys = ["person_id","date", "category_id"]
)
data class WorkAndCategoryCrossRef(
    @ColumnInfo(name = "category_id") val categoryId: String,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "person_id") val personId: UUID
)


enum class DayOfTheWork {
    MORNING, WHOLE_DAY, EVENING
}

@Entity(
    tableName = "group_work",
    foreignKeys = [ForeignKey(
        entity = LabourGroup::class,
        parentColumns = ["group_id"],
        childColumns = ["person_id"]
    )],
    primaryKeys = ["person_id", "date"]
)
class GroupWork(
    personId: UUID, date: Date, hours: Int, workDescription: String,
    var women: Int = 0,
    var men: Int = 0
) : WorkDetail(personId, date, hours, workDescription)

@Entity(
    tableName = "daily_work",
    foreignKeys = [ForeignKey(
        entity = Person::class,
        parentColumns = ["person_id"],
        childColumns = ["person_id"]
    )],
    primaryKeys = ["person_id", "date"],
    indices = [Index(value = ["person_id","date"], unique = true)]
//        Index(value = ["date"], unique = true),
//        Index(value = ["person_id"], unique = true)]
)
class DailyWork(
    personId: UUID, date: Date, hours: Int, workDescription: String,
    @ColumnInfo(name = "dailyWage") var dailyWage: Int,
    @ColumnInfo(name = "dayOfTheWork") var dayOfTheWork: DayOfTheWork = DayOfTheWork.MORNING,
) : WorkDetail(personId, date, hours, workDescription)



data class DailyWorkWithCategory(
    @Embedded
    var dailyWork: DailyWork,

    @Relation(
        parentColumn = "person_id",
        entityColumn = "category_id",
        associateBy = Junction(
            WorkAndCategoryCrossRef::class, parentColumn = "person_id",
            entityColumn = "category_id",
        )
    )
    var workCategory: WorkCategory
)

data class PersonWithWorkDetail(
    @Embedded val person: Person,
    @Relation(
        entity = DailyWork::class,
        parentColumn = "person_id",
        entityColumn = "person_id"
    )
    val workDetailList: List<DailyWorkWithCategory>
)