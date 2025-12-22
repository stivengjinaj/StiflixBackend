package org.stiveninc.stiflixbackend.controllers

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.stiveninc.stiflixbackend.dtos.MovieDto
import org.stiveninc.stiflixbackend.dtos.UserDto
import org.stiveninc.stiflixbackend.entities.MovieDocument
import org.stiveninc.stiflixbackend.entities.UserDocument
import org.stiveninc.stiflixbackend.services.UserService

@RestController
class UserController(private val userService: UserService) {

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
        @Valid @RequestBody user: UserDocument
    ) {
        return userService.save(user)
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/v2/users/continueWatching")
    fun getContinueWatching(
        @AuthenticationPrincipal userId: String,
    ): List<MovieDto> {
        return userService.getContinueWatching(userId)
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/v2/users/watched")
    fun watched(
        @AuthenticationPrincipal userId: String,
    ): List<MovieDto> {
        return userService.getWatched(userId)
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/v2/users/toWatch")
    fun toWatch(
        @AuthenticationPrincipal userId: String,
    ): List<MovieDto> {
        return userService.getToWatch(userId)
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/v2/users/continueWatching")
    @ResponseStatus(HttpStatus.CREATED)
    fun addToContinueWatching(
        @AuthenticationPrincipal userId: String,
        @Valid @RequestBody movie: MovieDocument
    ) {
        return userService.saveContinueWatching(userId, movie)
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/v2/users/watched")
    @ResponseStatus(HttpStatus.CREATED)
    fun addToWatched(
        @AuthenticationPrincipal userId: String,
        @Valid @RequestBody movie: MovieDocument
    ) {
        return userService.saveWatched(userId, movie)
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/v2/users/toWatch")
    @ResponseStatus(HttpStatus.CREATED)
    fun addToToWatch(
        @AuthenticationPrincipal userId: String,
        @Valid @RequestBody movie: MovieDocument
    ) {
        return userService.saveToWatch(userId, movie)
    }
}