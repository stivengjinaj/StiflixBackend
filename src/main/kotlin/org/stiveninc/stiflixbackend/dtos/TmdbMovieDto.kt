package org.stiveninc.stiflixbackend.dtos

import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

@Serializable
data class TmdbMovieDto(
    val id: Int,
    val title: String? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    val name: String? = null,
    val overview: String? = null,
    @SerialName("original_name")
    val originalName: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("genre_ids")
    val genresIds: List<Int>? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("media_type")
    val mediaType: String? = null,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int? = null,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int? = null,
    val seasons: List<TmdbSeasonDto>? = null,
    val genres: List<TmdbGenreDto>? = null
)
