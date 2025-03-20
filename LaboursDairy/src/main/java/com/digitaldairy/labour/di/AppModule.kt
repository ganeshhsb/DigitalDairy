package com.digitaldairy.labour.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase.QueryCallback
import com.digitaldairy.labour.FirebaseDataStore
import com.digitaldairy.labour.LabourRepository
import com.digitaldairy.labour.RoomDataStore
import com.digitaldairy.labour.data.AppDatabase
import com.digitaldairy.labour.data.dao.AddressDao
import com.digitaldairy.labour.data.dao.PeopleDao
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
    fun providePeopleDao(database: AppDatabase): PeopleDao {
        return database.peopleDao()
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database-name")
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