package com.chirag.sportzassignment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Bowling(
    @SerializedName("Style") val style: String ="-",
    @SerializedName("Average") val average: String,
    @SerializedName("Economyrate") val economyRate: String,
    @SerializedName("Wickets") val wickets: String
) :Parcelable {
    override fun toString(): String {
        return "Bowling(style='$style', average='$average', economyRate='$economyRate', wickets='$wickets')"
    }
}