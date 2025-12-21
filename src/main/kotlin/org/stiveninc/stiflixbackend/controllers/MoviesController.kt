package org.stiveninc.stiflixbackend.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MoviesController {

    @GetMapping("/api/v2/popularMovies")
    fun getPopularMovies(): List<String> {
        return listOf("Movie A", "Movie B", "Movie C")
    }
}