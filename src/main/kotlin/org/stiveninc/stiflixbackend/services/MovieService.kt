package org.stiveninc.stiflixbackend.services

import org.stiveninc.stiflixbackend.dtos.TmdbMovieDto

interface MovieService {
    suspend fun getPopularMovies(): List<TmdbMovieDto>
    fun getPopularTvShows(): List<TmdbMovieDto>
    fun getTopRatedMovies(): List<TmdbMovieDto>
    fun getTopRatedTvShows(): List<TmdbMovieDto>
    fun getTrendingMovies(): List<TmdbMovieDto>
    fun getTvShowDetails(tvShowId: Int): TmdbMovieDto
    fun discoverMovies(): List<TmdbMovieDto>
    fun discoverTvShows(): List<TmdbMovieDto>
    fun search(query: String): Pair<List<TmdbMovieDto>, List<TmdbMovieDto>>
    fun getTrailerKey(mediaType: String, mediaId: Int): String?
    fun mediaGenres(mediaType: String): Map<Int, String>
    fun mediaDetails(mediaType: String, mediaId: Int): Any
    fun getTvShowsSeasons(tvShowId: Int, seasonNumber: Int): Any
    fun getLogos(): List<String>
}