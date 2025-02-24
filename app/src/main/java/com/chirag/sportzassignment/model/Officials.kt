package com.chirag.sportzassignment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Officials(
    val Referee: String,
    val Umpires: String
):Parcelable {
    override fun toString(): String {
        return "Officials(Referee='$Referee', Umpires='$Umpires')"
    }
}