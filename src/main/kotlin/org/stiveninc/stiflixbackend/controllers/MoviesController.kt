package org.stiveninc.stiflixbackend.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.stiveninc.stiflixbackend.dtos.TmdbMovieDto
import org.stiveninc.stiflixbackend.services.MovieService

@RestController
class MoviesController(
    private val service: MovieService
) {

    @GetMapping("/api/v2/movies/popularMovies")
    suspend fun getPopularMovies(): List<TmdbMovieDto> {
        return service.getPopularMovies()
    }

    @GetMapping("/api/v2/movies/popularTvShows")
    suspend fun getPopularTvShows(): List<TmdbMovieDto> {
        return service.getPopularTvShows()
    }

    @GetMapping("/api/v2/movies/topRatedMovies")
    suspend fun getTopRatedMovies(): List<TmdbMovieDto> {
        return service.getTopRatedMovies()
    }

    @GetMapping("/api/v2/movies/topRatedTvShows")
    suspend fun getTopRatedTvShows(): List<TmdbMovieDto> {
        return service.getTopRatedTvShows()
    }

    @GetMapping("/api/v2/movies/trendingMovies")
    suspend fun getTrendingMovies(): List<TmdbMovieDto> {
        return service.getTrendingMovies()
    }

    @GetMapping("/api/v2/movies/discoverMovies")
    suspend fun discoverMovies(): List<TmdbMovieDto> {
        return service.discoverMovies()
    }

    @GetMapping("/api/v2/movies/discoverTvShows")
    suspend fun discoverTvShows(): List<TmdbMovieDto> {
        return service.discoverTvShows()
    }

    @GetMapping("/api/v2/movies/tvShowDetails/{tvShowId}")
    suspend fun getTvShowDetails(
        @PathVariable tvShowId: Int
    ): TmdbMovieDto {
        return service.getTvShowDetails(tvShowId)
    }

    @GetMapping("/api/v2/movies/search/{query}")
    suspend fun search(
        @PathVariable query: String
    ): Pair<List<TmdbMovieDto>, List<TmdbMovieDto>> {
        return service.search(query)
    }

    @GetMapping("/api/v2/movies/trailer/{mediaType}/{mediaId}")
    suspend fun getTrailerKey(
        @PathVariable mediaType: String,
        @PathVariable mediaId: Int
    ): List<String> {
        return service.getTrailerKey(mediaType, mediaId)
    }

    @GetMapping("/api/v2/movies/genres/{mediaType}")
    suspend fun mediaGenres(
        @PathVariable mediaType: String
    ): Map<Int, String> {
        return service.mediaGenres(mediaType)
    }

    @GetMapping("/api/v2/movies/details/{mediaType}/{mediaId}")
    suspend fun mediaDetails(
        @PathVariable mediaType: String,
        @PathVariable mediaId: Int
    ): Any {
        return service.mediaDetails(mediaType, mediaId)
    }

    @GetMapping("/api/v2/movies/tvShows/{tvShowId}/seasons/{seasonNumber}")
    suspend fun getTvShowsSeasons(
        @PathVariable tvShowId: Int,
        @PathVariable seasonNumber: Int
    ): Any {
        return service.getTvShowsSeasons(tvShowId, seasonNumber)
    }

    @GetMapping("/api/v2/movies/logos/{mediaType}/{mediaId}")
    suspend fun getLogos(
        @PathVariable mediaType: String,
        @PathVariable mediaId: Int
    ): List<String> {
        return service.getLogos(mediaType, mediaId)
    }
}