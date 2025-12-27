package org.stiveninc.stiflixbackend.entities

import com.google.cloud.Timestamp

data class MovieDocument(
    val mediaType: String? = null,
    val movieId: String? = null,
    val posterPath: String? = null,
    val date: Timestamp? = null,             // continueWatching collection
    val episode: Int? = null,               // continueWatching collection
    val season: Int? = null,                // continueWatching collection
)
