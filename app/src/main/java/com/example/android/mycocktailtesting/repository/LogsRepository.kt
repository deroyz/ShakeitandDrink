package com.example.android.mycocktailtesting.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.mycocktailtesting.database.DrinkDatabase
import com.example.android.mycocktailtesting.database.asDomainModelLog
import com.example.android.mycocktailtesting.domain.DomainLog
import com.example.android.mycocktailtesting.domain.asDatabaseModelLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LogsRepository(val database: DrinkDatabase) {

    val domainLogList: LiveData<List<DomainLog>> =
        Transformations.map(database.logDao.getLogs()) {
            it.asDomainModelLog()
        }

    suspend fun insertLog(domainLog: DomainLog) {
        withContext(Dispatchers.IO) {
            database.logDao.insertLog(domainLog.asDatabaseModelLog())
        }
    }

    suspend fun deleteLog(idLog: Int) {
        withContext(Dispatchers.IO) {
            database.logDao.deleteLog(idLog)
        }
    }

    suspend fun loadByLogId(logId: Int): DomainLog {
        return withContext(Dispatchers.IO) {
            database.logDao.loadByLogId(logId)
        }
    }
}