package org.stiveninc.stiflixbackend.dtos

import org.stiveninc.stiflixbackend.entities.MovieDocument

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
    date = date,
    episode = episode,
    season = season
)
