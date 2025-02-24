package com.chirag.sportzassignment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Batting(
    @SerializedName("Style") val style: String = "",
    @SerializedName("Average") val average: String = "",
    @SerializedName("Strikerate") val strikeRate: String = "",
    @SerializedName("Runs") val runs: String = ""
): Parcelable {
    override fun toString(): String {
        return "Batting(style='$style', average='$average', strikeRate='$strikeRate', runs='$runs')"
    }
}