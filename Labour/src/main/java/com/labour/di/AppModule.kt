package com.ganesh.compose.di

import android.content.Context
import androidx.room.Room
import com.ganesh.compose.data.AppDatabase
import com.ganesh.compose.data.dao.PeopleDao
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
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database-name").build()
    }
}