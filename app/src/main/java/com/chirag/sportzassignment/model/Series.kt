package com.chirag.sportzassignment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Series(
    val Id: String,
    val Name: String,
    val Status: String,
    val Tour: String,
    val Tour_Name: String
):Parcelable