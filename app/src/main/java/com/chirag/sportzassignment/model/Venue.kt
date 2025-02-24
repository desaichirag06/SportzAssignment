package com.chirag.sportzassignment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Venue(
    val Id: String,
    val Name: String
):Parcelable {
    override fun toString(): String {
        return "Venue(Id='$Id', Name='$Name')"
    }
}