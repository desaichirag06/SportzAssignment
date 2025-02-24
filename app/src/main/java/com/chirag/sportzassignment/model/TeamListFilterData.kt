package com.chirag.sportzassignment.model

data class TeamListFilterData(
    val id: Int,
    val teamName: String,
    var isSelected: Boolean = false
)