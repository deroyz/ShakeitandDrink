package com.example.android.mycocktailtesting.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.mycocktailtesting.database.DrinkDatabase
import com.example.android.mycocktailtesting.database.asDomainModelFavoriteDrink
import com.example.android.mycocktailtesting.database.asDomainModelLog
import com.example.android.mycocktailtesting.domain.Drink
import com.example.android.mycocktailtesting.domain.Log
import com.example.android.mycocktailtesting.domain.asDatabaseModelFavoriteDrink
import com.example.android.mycocktailtesting.domain.asDatabaseModelLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LogRepository(val database: DrinkDatabase) {

    val logList: LiveData<List<Log>> =
        Transformations.map(database.logDao.getLogs()) {
            it.asDomainModelLog()
        }

    suspend fun insertLog(log: Log) {
        withContext(Dispatchers.IO) {
            database.logDao.insertLog(log.asDatabaseModelLog())
        }
    }

    suspend fun deleteLog(idLog: Int) {
        withContext(Dispatchers.IO) {
            database.logDao.deleteLog(idLog)
        }
    }

    fun loadByLogId(logId: Int): Log {
        return database.logDao.loadByLogId(logId)
    }
}