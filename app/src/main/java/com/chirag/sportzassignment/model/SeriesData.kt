package com.chirag.sportzassignment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class SeriesData(
    val Matchdetail: Matchdetail,
    @SerializedName("Teams") val teams: Map<String, TeamData>?
) : Parcelable {
    override fun toString(): String {
        return "SeriesData(Matchdetail=$Matchdetail, teams=$teams)"
    }

    fun getTeamsList(): List<TeamData> {
        return teams!!.map { (key, team) ->
            team.copy(teamId = key, players = team.players?.mapValues { (playerKey, player) ->
                player.copy(playerId = playerKey)
            }) // Assign playerId as well
        }
    }
}
