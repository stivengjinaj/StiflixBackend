package org.stiveninc.stiflixbackend.entities

import java.time.LocalDateTime

data class MovieDocument(
    val mediaType: String? = null,
    val movieId: String? = null,
    val datetime: LocalDateTime? = null,    // continueWatching collection
    val episodeId: Int? = null,             // continueWatching collection
    val season: Int? = null,                // continueWatching collection
)
