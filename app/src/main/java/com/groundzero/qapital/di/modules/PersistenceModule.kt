package com.groundzero.qapital.di.modules

import android.content.Context
import androidx.room.Room
import com.groundzero.qapital.data.persistence.PersistenceDatabase
import com.groundzero.qapital.data.persistence.details.DetailsDao
import com.groundzero.qapital.data.persistence.goal.GoalDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): PersistenceDatabase {
        return Room.databaseBuilder(context, PersistenceDatabase::class.java, DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun providesGoalDao(database: PersistenceDatabase): GoalDao {
        return database.getGoalDao()
    }

    @Singleton
    @Provides
    fun providesDetailsDao(database: PersistenceDatabase): DetailsDao {
        return database.getDetailsDao()
    }

    companion object {
        private const val DATABASE_NAME = "persistence_database"
    }
}