package org.stiveninc.stiflixbackend.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserMoviesDto(
    @SerialName("favourites")
    val favourites: List<MovieDto>? = null,
    @SerialName("watchLater")
    val watchLater: List<MovieDto>? = null,
    @SerialName("watchList")
    val watchList: List<MovieDto>? = null,
    @SerialName("continueWatching")
    val continueWatching: List<MovieDto>? = null,
)
