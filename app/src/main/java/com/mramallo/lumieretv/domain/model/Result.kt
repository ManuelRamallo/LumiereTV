package com.mramallo.lumieretv.domain.model

data class Result(
    val details: List<Detail>,
    val id: Int,
    val title: String
)