package com.chirag.sportzassignment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Matchdetail(
    val Equation: String,
    val Match: Match,
    val Officials: Officials,
    val Player_Match: String,
    val Result: String,
    val Series: Series,
    val Status: String,
    val Status_Id: String,
    val Team_Away: String,
    val Team_Home: String,
    val Tosswonby: String,
    val Venue: Venue,
    val Weather: String,
    val Winmargin: String,
    val Winningteam: String
):Parcelable {
    override fun toString(): String {
        return "Matchdetail(Equation='$Equation', Match=$Match, Officials=$Officials, Player_Match='$Player_Match', Result='$Result', Series=$Series, Status='$Status', Status_Id='$Status_Id', Team_Away='$Team_Away', Team_Home='$Team_Home', Tosswonby='$Tosswonby', Venue=$Venue, Weather='$Weather', Winmargin='$Winmargin', Winningteam='$Winningteam')"
    }
}