package org.stiveninc.stiflixbackend.dtos

import kotlinx.serialization.Serializable
import org.stiveninc.stiflixbackend.entities.MovieDocument

@Serializable
data class MovieDto(
    val userId: String,
    val mediaType: String?,
    val movieId: String?,
    val date: String?,              // continueWatching collection
    val episode: Int?,              // continueWatching collection
    val season: Int?,               // continueWatching collection
)

fun MovieDocument.toDto(userId: String) = MovieDto(
    userId = userId,
    mediaType = mediaType,
    movieId = movieId,
    date = date?.toDate().toString(),
    episode = episode,
    season = season
)
