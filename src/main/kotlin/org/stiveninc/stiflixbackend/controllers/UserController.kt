package org.stiveninc.stiflixbackend.controllers

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.stiveninc.stiflixbackend.config.setUserRole
import org.stiveninc.stiflixbackend.dtos.MovieDto
import org.stiveninc.stiflixbackend.dtos.UserDto
import org.stiveninc.stiflixbackend.dtos.UserMoviesDto
import org.stiveninc.stiflixbackend.entities.MovieDocument
import org.stiveninc.stiflixbackend.entities.UserDocument
import org.stiveninc.stiflixbackend.enums.UserRole
import org.stiveninc.stiflixbackend.services.UserService

@RestController
class UserController(private val userService: UserService) {

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/api/v2/admin/{userId}/role")
    fun setRole(
        @PathVariable userId: String,
        @RequestParam role: UserRole
    ) {
        setUserRole(userId, role)
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/v2/users/me")
    fun getUserData(
        @AuthenticationPrincipal userId: String,
    ): UserDto {
        return userService.getUserById(userId)
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/api/v2/users/")
    fun updateUserVerification(
        @AuthenticationPrincipal userId: String,
    ){
        return userService.updateUserVerification(userId)
    }

    @PostMapping("/api/v2/users/")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveUser(
        @AuthenticationPrincipal userId: String,
        @Valid @RequestBody user: UserDocument
    ) {
        return userService.save(userId, user)
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/v2/users/continueWatching")
    fun getContinueWatching(
        @AuthenticationPrincipal userId: String,
    ): List<MovieDto> {
        return userService.getContinueWatching(userId)
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/v2/users/continueWatching")
    @ResponseStatus(HttpStatus.CREATED)
    fun addToContinueWatching(
        @AuthenticationPrincipal userId: String,
        @Valid @RequestBody movie: MovieDocument
    ): ResponseEntity<Boolean> {
        userService.saveContinueWatching(userId, movie)
        return ResponseEntity.ok(true)
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/v2/users/watchList")
    @ResponseStatus(HttpStatus.CREATED)
    fun addToWatchList(
        @AuthenticationPrincipal userId: String,
        @Valid @RequestBody movie: MovieDocument
    ): ResponseEntity<Boolean> {
        userService.saveToWatchList(userId, movie)
        return ResponseEntity.ok(true)
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/v2/users/watchLater")
    @ResponseStatus(HttpStatus.CREATED)
    fun addToWatchLater(
        @AuthenticationPrincipal userId: String,
        @Valid @RequestBody movie: MovieDocument
    ): ResponseEntity<Boolean> {
        userService.saveToWatchLater(userId, movie)
        return ResponseEntity.ok(true)
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/v2/users/favourites")
    fun addToFavourites(
        @AuthenticationPrincipal userId: String,
        @Valid @RequestBody movie: MovieDocument
    ): ResponseEntity<Boolean> {
        userService.saveToFavourites(userId, movie)
        return ResponseEntity.ok(true)
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/api/v2/users/favourites/{movieId}")
    fun removeFromFavourites(
        @AuthenticationPrincipal userId: String,
        @PathVariable movieId: String
    ): ResponseEntity<Boolean> {
        userService.removeFromFavourites(userId, movieId)
        return ResponseEntity.ok(true)
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/api/v2/users/continueWatching/{movieId}")
    fun removeFromContinueWatching(
        @AuthenticationPrincipal userId: String,
        @PathVariable movieId: String
    ): ResponseEntity<Boolean> {
        userService.removeFromContinueWatching(userId, movieId)
        return ResponseEntity.ok(true)
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/api/v2/users/watchLater/{movieId}")
    fun removeFromWatchLater(
        @AuthenticationPrincipal userId: String,
        @PathVariable movieId: String
    ): ResponseEntity<Boolean> {
        userService.removeFromWatchLater(userId, movieId)
        return ResponseEntity.ok(true)
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/api/v2/users/watchList/{movieId}")
    fun removeFromWatchList(
        @AuthenticationPrincipal userId: String,
        @PathVariable movieId: String
    ): ResponseEntity<Boolean> {
        userService.removeFromWatchList(userId, movieId)
        return ResponseEntity.ok(true)
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/v2/users/movies")
    fun getUserMovies(
        @AuthenticationPrincipal userId: String,
    ): UserMoviesDto{
        return userService.getUserMovies(userId)
    }
}