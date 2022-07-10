package com.example.android.mycocktailtesting.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.example.android.mycocktailtesting.database.DatabaseLog

@Parcelize
data class DomainLog(
    var idLog: Int = 0,
    var nameLog: String = "",
    var priceLog: Double = 0.0,
    var ratingLog: Double = 0.0,
    var placeLog: String= " ",
//    val dateLog: Date,
    var commentLog: String = " "
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
