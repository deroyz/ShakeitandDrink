package com.example.android.mycocktailtesting.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.mycocktailtesting.data.domain.DomainLog

@Dao
interface LogDao {
    @Query("SELECT * FROM logList")
    fun getLogs(): LiveData<List<DatabaseLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLog(newLog: DatabaseLog)

    @Query("DELETE FROM logList WHERE idLog = :id")
    fun deleteLog(id: Int)

    @Query("SELECT * from logList WHERE idLog = :id")
    fun loadByLogId(id: Int): DomainLog
}