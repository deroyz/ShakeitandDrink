package com.example.android.mycocktailtesting.addlog

import android.app.Application
import androidx.lifecycle.*
import com.example.android.mycocktailtesting.database.getDatabase
import com.example.android.mycocktailtesting.domain.Log
import com.example.android.mycocktailtesting.repository.LogRepository
import kotlinx.coroutines.launch

class AddLogViewModel(application: Application, logId: Int) : AndroidViewModel(application) {

    companion object {
        const val DEFAULT_LOG_ID = -1
    }

    private val database = getDatabase(application)
    private val logRepository = LogRepository(database)

    val log = MutableLiveData<Log>()

    init {
        if (logId != DEFAULT_LOG_ID) {
            viewModelScope.launch {
                log.value = logRepository.loadByLogId(logId)
            }
        }
    }



    fun onAddButtonClicked(log: Log) {
//        viewModelScope.launch {
//            logRepository.insertLog(log)
//        }
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
