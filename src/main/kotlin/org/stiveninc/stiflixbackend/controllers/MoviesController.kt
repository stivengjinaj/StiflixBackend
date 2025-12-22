package org.stiveninc.stiflixbackend.controllers

import org.springframework.web.bind.annotation.GetMapping
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
}