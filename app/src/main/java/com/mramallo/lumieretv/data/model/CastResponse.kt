package com.mramallo.lumieretv.data.model

data class CastResponse(
    val cast: List<Cast> = listOf(),
    val crew: List<Crew> = listOf(),
    val id: Int = 0
)
