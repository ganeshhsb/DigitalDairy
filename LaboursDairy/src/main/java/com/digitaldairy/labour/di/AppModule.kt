package com.digitaldairy.labour.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase.QueryCallback
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.digitaldairy.labour.FirebaseDataStore
import com.digitaldairy.labour.repo.LabourRepository
import com.digitaldairy.labour.RoomDataStore
import com.digitaldairy.labour.WagePreferencesManager
import com.digitaldairy.labour.data.AppDatabase
import com.digitaldairy.labour.data.dao.AddressDao
import com.digitaldairy.labour.data.dao.PersonDao
import com.digitaldairy.labour.data.dao.WorkDetailDao
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.PersistentCacheSettings
import com.google.firebase.firestore.firestoreSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun providePeopleDao(database: AppDatabase): PersonDao {
        return database.personDao()
    }

    @Provides
    fun provideWorkDetailDao(database: AppDatabase): WorkDetailDao {
        return database.workDetailDao()
    }

    @Provides
    fun provideAddressDao(database: AppDatabase): AddressDao {
        return database.addressDao()
    }

    @Provides
    fun provideFirebaseDataStore(): FirebaseDataStore {
        val db = FirebaseFirestore.getInstance()

        FirebaseFirestore.getInstance().firestoreSettings = firestoreSettings {
            setLocalCacheSettings(PersistentCacheSettings.newBuilder().build())
        }
        return FirebaseDataStore(db)
    }

    @Provides
    fun provideRoomDataStore(database: AppDatabase): RoomDataStore {
        return RoomDataStore(database)
    }

    @Provides
    fun provideLabourRepository(
        roomDataStore: RoomDataStore,
        firebaseDataStore: FirebaseDataStore
    ): LabourRepository {
        return LabourRepository(roomDataStore, firebaseDataStore)
    }


    @Provides
    @Singleton
    fun providePrefDataStore(@ApplicationContext context: Context): WagePreferencesManager {
        return WagePreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database-name")
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .fallbackToDestructiveMigrationOnDowngrade()
            .setQueryCallback(
                queryCallback = object : QueryCallback {
                    override fun onQuery(sqlQuery: String, bindArgs: List<Any?>) {
                        Log.d("RoomQuery", "SQL: $sqlQuery")
                        Log.d("RoomQuery", "Args: $bindArgs")
                    }
                },
                executor = Executors.newSingleThreadExecutor()
            )
            .build()
    }
}

val MIGRATION_1_2 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add new column with a default value
        database.execSQL("ALTER TABLE work_detail ADD COLUMN category TEXT NOT NULL DEFAULT 'General'")

        // Create the new WorkCategory table
        database.execSQL(
            """
            CREATE TABLE work_category (
                work_detail_id TEXT NOT NULL PRIMARY KEY,
                category_name TEXT NOT NULL,
                description TEXT NOT NULL
            )
        """.trimIndent()
        )
    }
}