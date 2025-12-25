package org.stiveninc.stiflixbackend.controllers

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.stiveninc.stiflixbackend.dtos.TmdbMovieDto
import org.stiveninc.stiflixbackend.dtos.TmdbPagedResponse
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
    suspend fun getTrendingMovies(): TmdbPagedResponse<TmdbMovieDto> {
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
    ): TmdbPagedResponse<TmdbMovieDto> {
        return service.search(query)
    }

    @GetMapping("/api/v2/movies/trailer/{mediaType}/{mediaId}")
    suspend fun getTrailerKey(
        @PathVariable mediaType: String,
        @PathVariable mediaId: Int
    ): List<String> {
        return service.getTrailerKey(mediaType, mediaId)
    }

    @GetMapping("/api/v2/movies/genres/{mediaId}/{mediaType}")
    suspend fun mediaGenres(
        @PathVariable mediaId: Int,
        @PathVariable mediaType: String
    ): Map<Int, String> {
        return service.mediaGenres(mediaId, mediaType)
    }

    @GetMapping("/api/v2/movies/details/{mediaType}/{mediaId}")
    suspend fun mediaDetails(
        @PathVariable mediaType: String,
        @PathVariable mediaId: Int
    ): ResponseEntity<String> =
        ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(service.mediaDetails(mediaType, mediaId))

    @GetMapping("/api/v2/movies/tv/{tvShowId}/seasons/{seasonNumber}")
    suspend fun getTvShowsSeasons(
        @PathVariable tvShowId: Int,
        @PathVariable seasonNumber: Int
    ): ResponseEntity<String> =
        ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(service.getTvShowsSeasons(tvShowId, seasonNumber))

    @GetMapping("/api/v2/movies/logos/{mediaType}/{mediaId}")
    suspend fun getLogos(
        @PathVariable mediaType: String,
        @PathVariable mediaId: Int
    ): ResponseEntity<String> =
        ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(service.getLogos(mediaType, mediaId))

    @PreAuthorize("hasRole('OWNER') or hasRole('EDITOR')")
    @GetMapping("/api/v2/stiflixchill/home/{page}")
    suspend fun getStiflixChillHome(
        @PathVariable page: Int = 1
    ): TmdbPagedResponse<TmdbMovieDto> {
        return service.getStiflixChillHome(page)
    }

    @PreAuthorize("isAuthenticated() or hasRole('OWNER') or hasRole('EDITOR')")
    @GetMapping("/api/v2/stiflixchill/communication")
    suspend fun getStiflixChillCommunication(): ResponseEntity<String> =
        ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body("Communication")
}