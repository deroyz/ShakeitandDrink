package com.example.android.mycocktailtesting.model.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.example.android.mycocktailtesting.model.database.DatabaseLog

@Parcelize
data class DomainLog(
    val idLog: Int = 0,
    val nameLog: String = "",
    val priceLog: Double = 0.0,
    val ratingLog: Double = 0.0,
    val placeLog: String= " ",
//    val dateLog: Date,
    val commentLog: String = " "
) : Parcelable {

}

fun DomainLog.asDatabaseModelLog(): DatabaseLog {
    return DatabaseLog(
        idLog = idLog,
        nameLog = nameLog,
        priceLog = priceLog,
        ratingLog = ratingLog,
        placeLog = placeLog,
//        dateLog = dateLog,
        commentLog = commentLog
    )
}
