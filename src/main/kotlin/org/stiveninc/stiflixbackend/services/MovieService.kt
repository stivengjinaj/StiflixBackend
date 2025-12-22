package org.stiveninc.stiflixbackend.services

import org.stiveninc.stiflixbackend.dtos.TmdbMovieDto

interface MovieService {
    suspend fun getPopularMovies(): List<TmdbMovieDto>
    suspend fun getPopularTvShows(): List<TmdbMovieDto>
    suspend fun getTopRatedMovies(): List<TmdbMovieDto>
    suspend fun getTopRatedTvShows(): List<TmdbMovieDto>
    suspend fun getTrendingMovies(): List<TmdbMovieDto>
    suspend fun getTvShowDetails(tvShowId: Int): TmdbMovieDto
    suspend fun discoverMovies(): List<TmdbMovieDto>
    suspend fun discoverTvShows(): List<TmdbMovieDto>
    suspend fun search(query: String): Pair<List<TmdbMovieDto>, List<TmdbMovieDto>>
    suspend fun getTrailerKey(mediaType: String, mediaId: Int): List<String>
    suspend fun mediaGenres(mediaType: String): Map<Int, String>
    suspend fun mediaDetails(mediaType: String, mediaId: Int): Any
    suspend fun getTvShowsSeasons(tvShowId: Int, seasonNumber: Int): Any
    suspend fun getLogos(mediaType: String, mediaId: Int): List<String>
}