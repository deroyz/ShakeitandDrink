package com.example.android.mycocktailtesting.log.logs

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.mycocktailtesting.data.database.getDatabase
import com.example.android.mycocktailtesting.data.domain.DomainLog

import com.example.android.mycocktailtesting.data.repository.LogsRepository

class LogsViewModel(application: Application) : AndroidViewModel(application) {

//    val navigateToSelectedLog = MutableLiveData<DomainLog>()

    private val database = getDatabase(application)
    private val logsRepository = LogsRepository(database)

    val logList = logsRepository.domainLogList

    init {
        Log.e("LogsViewModel", "1")
    }

    fun navigateToSelectedLogComplete() {
//        navigateToSelectedLog.value = null
    }

    fun navigateToSelectedLog(log: DomainLog) {
//        navigateToSelectedLog.value = log
        Log.e("LogsViewModel", "navigateToSelectedLog")
    }
}

class LogsViewModelFactory(private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogsViewModel::class.java)) {
            Log.e("ViewModelFactory", "LogsViewModel Assigned")
            return LogsViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}