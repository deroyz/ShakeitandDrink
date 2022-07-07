package com.example.android.mycocktailtesting.domain

import android.os.Parcelable
import androidx.room.Ignore
import kotlinx.parcelize.Parcelize
import com.example.android.mycocktailtesting.database.DatabaseLog

@Parcelize
data class Log(
    @Ignore
    val idLog: Int,
    val nameLog: String,
    val priceLog: Double,
    val ratingLog: String,
    val placeLog: String,
//    val dateLog: Date,
    val commentLog: String
) : Parcelable {

}

fun Log.asDatabaseModelLog(): DatabaseLog {
    return DatabaseLog(
        idLog = idLog,
        nameLog = nameLog,
        priceLog =  priceLog,
        ratingLog = ratingLog,
        placeLog = placeLog,
//        dateLog = dateLog,
        commentLog = commentLog
    )
}
