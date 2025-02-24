package com.chirag.sportzassignment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class PlayerData(
    var playerId: String = "",  // Store player key
    @SerializedName("Position") val position: String = "0",
    @SerializedName("Name_Full") val fullName: String = "",
    @SerializedName("Iskeeper") val isKeeper: Boolean = false,
    @SerializedName("Iscaptain") val isCaptain: Boolean = false,
    @SerializedName("Batting") val batting: Batting,
    @SerializedName("Bowling") val bowling: Bowling
): Parcelable{
    override fun toString(): String {
        return "PlayerData(position=$position, fullName='$fullName', isKeeper=$isKeeper, isCaptain=$isCaptain, batting=$batting, bowling=$bowling)"
    }
}