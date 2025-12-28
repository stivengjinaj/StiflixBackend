package org.stiveninc.stiflixbackend.services

import org.stiveninc.stiflixbackend.dtos.TmdbMovieDto
import org.stiveninc.stiflixbackend.dtos.TmdbPagedResponse
import org.stiveninc.stiflixbackend.entities.CommunicationPhrase

interface MovieService {
    suspend fun getPopularMovies(): List<TmdbMovieDto>
    suspend fun getPopularTvShows(): List<TmdbMovieDto>
    suspend fun getTopRatedMovies(): List<TmdbMovieDto>
    suspend fun getTopRatedTvShows(): List<TmdbMovieDto>
    suspend fun getTrendingMovies(): TmdbPagedResponse<TmdbMovieDto>
    suspend fun getTvShowDetails(tvShowId: Int): TmdbMovieDto
    suspend fun discoverMovies(): List<TmdbMovieDto>
    suspend fun discoverTvShows(): List<TmdbMovieDto>
    suspend fun search(query: String): TmdbPagedResponse<TmdbMovieDto>
    suspend fun getTrailerKey(mediaType: String, mediaId: Int): List<String>
    suspend fun mediaGenres(mediaId: Int, mediaType: String): Map<Int, String>
    suspend fun mediaDetails(mediaType: String, mediaId: Int): String
    suspend fun getTvShowsSeasons(tvShowId: Int, seasonNumber: Int): String
    suspend fun getLogos(mediaType: String, mediaId: Int): String
    suspend fun getStiflixChillHome(page: Int): TmdbPagedResponse<TmdbMovieDto>
    suspend fun getStiflixChillCommunication(): List<CommunicationPhrase>
    suspend fun saveStiflixChillCommunication(communicationPhrase: CommunicationPhrase): Boolean
    suspend fun updateStiflixChillCommunication(communicationPhrase: CommunicationPhrase): Boolean
    suspend fun deleteStiflixChillCommunication(id: String): Boolean
}