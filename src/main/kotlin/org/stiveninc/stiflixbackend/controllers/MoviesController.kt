package org.stiveninc.stiflixbackend.controllers

import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.stiveninc.stiflixbackend.dtos.TmdbMovieDto
import org.stiveninc.stiflixbackend.dtos.TmdbPagedResponse
import org.stiveninc.stiflixbackend.entities.CommunicationPhrase
import org.stiveninc.stiflixbackend.services.MovieService

@RestController
class MoviesController(
    private val service: MovieService,
) {

    @GetMapping("/api/v2/movies/popularMovies")
    fun getPopularMovies(): List<TmdbMovieDto> {
        return service.getPopularMovies()
    }

    @GetMapping("/api/v2/movies/popularTvShows")
    fun getPopularTvShows(): List<TmdbMovieDto> {
        return service.getPopularTvShows()
    }

    @GetMapping("/api/v2/movies/topRatedMovies")
    fun getTopRatedMovies(): List<TmdbMovieDto> {
        return service.getTopRatedMovies()
    }

    @GetMapping("/api/v2/movies/topRatedTvShows")
    fun getTopRatedTvShows(): List<TmdbMovieDto> {
        return service.getTopRatedTvShows()
    }

    @GetMapping("/api/v2/movies/trendingMovies")
    fun getTrendingMovies(): TmdbPagedResponse<TmdbMovieDto> {
        return service.getTrendingMovies()
    }

    @GetMapping("/api/v2/movies/discoverMovies")
    fun discoverMovies(): List<TmdbMovieDto> {
        return service.discoverMovies()
    }

    @GetMapping("/api/v2/movies/discoverTvShows")
    fun discoverTvShows(): List<TmdbMovieDto> {
        return service.discoverTvShows()
    }

    @GetMapping("/api/v2/movies/tvShowDetails/{tvShowId}")
    fun getTvShowDetails(
        @PathVariable tvShowId: Int
    ): TmdbMovieDto {
        return service.getTvShowDetails(tvShowId)
    }

    @GetMapping("/api/v2/movies/search/{query}")
    fun search(
        @PathVariable query: String
    ): TmdbPagedResponse<TmdbMovieDto> {
        return service.search(query)
    }

    @GetMapping("/api/v2/movies/trailer/{mediaType}/{mediaId}")
    fun getTrailerKey(
        @PathVariable mediaType: String,
        @PathVariable mediaId: Int
    ): List<String> {
        return service.getTrailerKey(mediaType, mediaId)
    }

    @GetMapping("/api/v2/movies/genres/{mediaId}/{mediaType}")
    fun mediaGenres(
        @PathVariable mediaId: Int,
        @PathVariable mediaType: String
    ): Map<Int, String> {
        return service.mediaGenres(mediaId, mediaType)
    }

    @GetMapping("/api/v2/movies/details/{mediaType}/{mediaId}")
    fun mediaDetails(
        @PathVariable mediaType: String,
        @PathVariable mediaId: Int
    ): ResponseEntity<String> =
        ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(service.mediaDetails(mediaType, mediaId))

    @GetMapping("/api/v2/movies/tv/{tvShowId}/seasons/{seasonNumber}")
    fun getTvShowsSeasons(
        @PathVariable tvShowId: Int,
        @PathVariable seasonNumber: Int
    ): ResponseEntity<String> =
        ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(service.getTvShowsSeasons(tvShowId, seasonNumber))

    @GetMapping("/api/v2/movies/logos/{mediaType}/{mediaId}")
    fun getLogos(
        @PathVariable mediaType: String,
        @PathVariable mediaId: Int
    ): ResponseEntity<String> =
        ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(service.getLogos(mediaType, mediaId))

    @PreAuthorize("hasRole('OWNER') or hasRole('EDITOR')")
    @GetMapping("/api/v2/stiflixchill/home/{page}")
    fun getStiflixChillHome(
        @PathVariable page: Int
    ): TmdbPagedResponse<TmdbMovieDto> {
        return service.getStiflixChillHome(page)
    }

    @PreAuthorize("hasRole('OWNER') or hasRole('EDITOR')")
    @GetMapping("/api/v2/stiflixchill/communication")
    fun getStiflixChillCommunication(): List<CommunicationPhrase> {
        return service.getStiflixChillCommunication()
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/api/v2/admin/stiflixchill/communication")
    fun updateStiflixChillCommunication(
        @Valid @RequestBody communicationPhrase: CommunicationPhrase
    ): ResponseEntity<Boolean> {
        val communication = service.updateStiflixChillCommunication(communicationPhrase)
        return ResponseEntity.ok(communication)
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/api/v2/admin/stiflixchill/communication")
    fun saveStiflixChillCommunication(
        @Valid @RequestBody communicationPhrase: CommunicationPhrase
    ): ResponseEntity<Boolean> {
        val communication = service.saveStiflixChillCommunication(communicationPhrase)
        return ResponseEntity.ok(communication)
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/api/v2/admin/stiflixchill/communication/{id}")
    fun deleteStiflixChill(
        @PathVariable id: String
    ): ResponseEntity<Boolean> {
        service.deleteStiflixChillCommunication(id)
        return ResponseEntity.ok(true)
    }
}