package com.chirag.sportzassignment.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeStamp {
    fun ddThMMMyyyy(date: Date): String {
        val dayNumberSuffix = getDayNumberSuffix(date.date)
        return SimpleDateFormat("dd'$dayNumberSuffix' MMM, yyyy", Locale.ENGLISH).format(date.time)
    }


    private fun getDayNumberSuffix(day: Int): String {
        if (day in 11..13) {
            return "th"
        }
        return when (day % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }


    fun apiDateStringToDateObj(givenDateString: String): Date? {
        try {
            val sdf = SimpleDateFormat(
                "M/dd/yyyy",
                Locale.ENGLISH
            )
            return sdf.parse(givenDateString)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return Date()
    }
}