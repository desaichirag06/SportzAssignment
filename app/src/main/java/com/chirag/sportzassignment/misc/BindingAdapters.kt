package com.chirag.sportzassignment.misc

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.chirag.sportzassignment.R
import com.chirag.sportzassignment.utils.TimeStamp

@BindingAdapter("backgroundPoster")
fun setBackgroundPoster(imageView: ImageView, seriesName: String?) {
    when {
        seriesName != null -> if (seriesName.contains("Pakistan") && seriesName.contains(
                "South Africa"
            )
        ) {
            Glide.with(imageView.context).load(R.drawable.pk_vs_sa_background_image)
                .into(imageView)
        } else Glide.with(imageView.context).load(R.drawable.ind_vs_nz_background_image)
            .into(imageView)

        else -> Glide.with(imageView.context).load(R.drawable.poster_placeholder)
            .into(imageView)
    }
}


@BindingAdapter("matchDate", "matchTime")
fun setMatchDateTime(textView: TextView, matchDate: String?, matchTime: String?) {
    when {
        matchDate != null && matchTime != null -> {
            val dateObj = TimeStamp.apiDateStringToDateObj(matchDate)
            val formattedDateNew = TimeStamp.ddThMMMyyyy(dateObj!!)
            textView.text = "On $formattedDateNew at $matchTime"
        }

        else -> {
            textView.text = ""
        }
    }
}

@BindingAdapter("playerName", "isCaptain", "isKeeper")
fun setNameWithPosition(
    textView: TextView,
    playerName: String?,
    isCaptain: Boolean,
    isKeeper: Boolean
) {
    when {
        playerName != null -> when {
            isCaptain && isKeeper -> textView.text = "$playerName (C & Wk)"
            isCaptain -> textView.text = "$playerName (C)"
            isKeeper -> textView.text = "$playerName (Wk)"
            else -> textView.text = playerName
        }

        else -> textView.text = ""
    }
}


@BindingAdapter("battingStyle")
fun setBattingStyle(textView: TextView, battingStyle: String?) {
    when {
        battingStyle != null -> when (battingStyle) {
            "RHB" -> textView.text = "Right Handed Batsman"
            else -> textView.text = "Left Handed Batsman"
        }

        else -> textView.text = ""
    }
}

@BindingAdapter("bowlingStyle")
fun setBowlingStyle(textView: TextView, bowlingStyle: String?) {
    when {
        bowlingStyle != null ->
            when (bowlingStyle) {
                "LF" -> textView.text = "Left Fast"
                "LFM" -> textView.text = "Left Fast Medium"
                "LMF" -> textView.text = "Left Medium Fast"
                "MF" -> textView.text = "Medium Fast"
                "RF" -> textView.text = "Right Fast"
                "RFM" -> textView.text = "Right Fast Medium"
                "RMF" -> textView.text = "Right Medium Fast"
                "RM" -> textView.text = "Right Medium"
                "LM" -> textView.text = "Left Medium"
                "SLO" -> textView.text = "Slow Pace"
                "FAST" -> textView.text = "Fast Pace"
                "OB" -> textView.text = "Off Break"
                "LB" -> textView.text = "Leg Break"
                "LBG" -> textView.text = "Leg Break Googly"
                "SLA" -> textView.text = "Slow Left Arm Orthodox"
                "SLC" -> textView.text = "Slow Left Arm Chinaman"
                "OS" -> textView.text = "Off Spin"
                "LS" -> textView.text = "Leg Spin"
                "SPIN" -> textView.text = "Spin Pace"
                else -> textView.text = "Unknown Bowling Style"
            }

        else -> textView.text = "-"
    }
}


