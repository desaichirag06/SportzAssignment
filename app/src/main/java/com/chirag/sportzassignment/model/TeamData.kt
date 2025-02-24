package com.chirag.sportzassignment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamData(
    var teamId: String = "",  // Store key ("4", "5", etc.)
    @SerializedName("Name_Full") val fullName: String = "",
    @SerializedName("Name_Short") val shortName: String = "",
    @SerializedName("Players") val players: Map<String, PlayerData>?
) : Parcelable {
    override fun toString(): String {
        return "TeamData(teamId='$teamId', fullName='$fullName', shortName='$shortName', players=$players)"
    }

    fun getPlayerList(): ArrayList<PlayerData> {
        return ArrayList(players!!.map { (key, player) ->
            player.copy(playerId = key) // Assign playerId dynamically
        })
    }
}