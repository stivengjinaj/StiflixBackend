package org.stiveninc.stiflixbackend.entities

data class MovieDocument(
    val mediaType: String? = null,
    val movieId: String? = null,
    val date: String? = null,               // continueWatching collection
    val episode: Int? = null,               // continueWatching collection
    val season: Int? = null,                // continueWatching collection
)
