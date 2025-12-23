package org.stiveninc.stiflixbackend.services

import org.stiveninc.stiflixbackend.dtos.TmdbMovieDto
import org.stiveninc.stiflixbackend.dtos.TmdbPagedResponse

interface MovieService {
    fun getPopularMovies(): List<TmdbMovieDto>
    fun getPopularTvShows(): List<TmdbMovieDto>
    fun getTopRatedMovies(): List<TmdbMovieDto>
    fun getTopRatedTvShows(): List<TmdbMovieDto>
    fun getTrendingMovies(): TmdbPagedResponse<TmdbMovieDto>
    fun getTvShowDetails(tvShowId: Int): TmdbMovieDto
    fun discoverMovies(): List<TmdbMovieDto>
    fun discoverTvShows(): List<TmdbMovieDto>
    fun search(query: String): TmdbPagedResponse<TmdbMovieDto>
    fun getTrailerKey(mediaType: String, mediaId: Int): List<String>
    fun mediaGenres(mediaId: Int, mediaType: String): Map<Int, String>
    fun mediaDetails(mediaType: String, mediaId: Int): String
    fun getTvShowsSeasons(tvShowId: Int, seasonNumber: Int): String
    fun getLogos(mediaType: String, mediaId: Int): String
}