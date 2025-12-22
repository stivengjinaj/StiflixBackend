package org.stiveninc.stiflixbackend.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TmdbVideoDto(
    val key: String,
    val site: String? = null,
    val type: String? = null,
    @SerialName("iso_639_1")
    val language: String? = null
)

@Serializable
data class TmdbVideosResponse(
    val id: Int,
    val results: List<TmdbVideoDto>
)

@Serializable
data class TmdbPagedResponse<T>(
    val page: Int,
    val results: List<T>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)

@Serializable
data class TmdbGenreDto(
    val id: Int,
    val name: String
)

@Serializable
data class TmdbGenresResponse(
    val genres: List<TmdbGenreDto>
)

@Serializable
data class TmdbLogoDto(
    @SerialName("file_path")
    val filePath: String,
    val width: Int? = null,
    val height: Int? = null,
    @SerialName("iso_639_1")
    val language: String? = null
)

@Serializable
data class TmdbImagesResponse(
    val logos: List<TmdbLogoDto> = emptyList()
)