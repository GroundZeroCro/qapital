package com.groundzero.qapital.base

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.groundzero.qapital.data.persistence.PersistenceDatabase
import org.junit.Before

open class DaoBaseTest {

    protected lateinit var persistenceDatabase: PersistenceDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        persistenceDatabase = Room.inMemoryDatabaseBuilder(
            context, PersistenceDatabase::class.java
        ).build()
    }
}