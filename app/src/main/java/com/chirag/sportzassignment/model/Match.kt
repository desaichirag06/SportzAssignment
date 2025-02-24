package com.chirag.sportzassignment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Match(
    val Code: String,
    val Date: String,
    val Daynight: String,
    val Id: String,
    val League: String,
    val Livecoverage: String,
    val Number: String,
    val Offset: String,
    val Time: String,
    val Type: String
):Parcelable {
    override fun toString(): String {
        return "Match(Code='$Code', Date='$Date', Daynight='$Daynight', Id='$Id', League='$League', Livecoverage='$Livecoverage', Number='$Number', Offset='$Offset', Time='$Time', Type='$Type')"
    }
}