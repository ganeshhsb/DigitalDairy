package com.digitaldairy.labour.di

import android.content.Context
import androidx.room.Room
import com.digitaldairy.labour.data.AppDatabase
import com.digitaldairy.labour.data.dao.AddressDao
import com.digitaldairy.labour.data.dao.PeopleDao
import com.digitaldairy.labour.data.dao.WorkDetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database-name").build()
    }
}