package com.example.android.mycocktailtesting.addlog

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.android.mycocktailtesting.database.getDatabase
import com.example.android.mycocktailtesting.domain.DomainLog
import com.example.android.mycocktailtesting.repository.LogRepository
import kotlinx.coroutines.launch

class AddLogViewModel(application: Application, private val logId: Int) :
    AndroidViewModel(application) {

    companion object {
        const val ADD_LOG_ID = 0
    }

    private val database = getDatabase(application)
    private val logRepository = LogRepository(database)

    val domainLog = MutableLiveData<DomainLog>()

    init {
        Log.e("AddLogViewModel", "1")
        if (logId != ADD_LOG_ID) {
            Log.e("AddLogViewModel", "2")
            viewModelScope.launch {
                domainLog.value = logRepository.loadByLogId(logId)
            }
        }
    }

    fun onAddButtonClicked(logEntry: DomainLog) {

        Log.e("AddLogViewModel", "${logEntry.nameLog}")

        if (logId == ADD_LOG_ID) {
            viewModelScope.launch {
                Log.e("AddLogViewModel", "4")
                logRepository.insertLog(logEntry)
            }
        }
    }

    fun takePhoto() {

    }

    fun openGallery() {

    }

}

class AddLogViewModelFactory(private val app: Application, private val logId: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AddLogViewModel::class.java)) {
            android.util.Log.e("ViewModelFactory", "AddLogViewModelFactory Assigned")
            return AddLogViewModel(app, logId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
