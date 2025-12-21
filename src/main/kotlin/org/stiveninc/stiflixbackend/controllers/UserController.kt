package org.stiveninc.stiflixbackend.controllers

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
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

    @GetMapping("/api/v2/users/id/{userId}")
    fun getUserById(
        @PathVariable("userId") userId: String
    ): UserDto {
        return userService.getUserById(userId)
    }

    @GetMapping("/api/v2/users/{userEmail}")
    fun getUserByEmail(
        @PathVariable("userEmail") userEmail: String
    ): UserDto {
        return userService.getUserByEmail(userEmail)
    }

    @PostMapping("/api/v2/users/")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveUser(
        @Valid @RequestBody user: UserDocument
    ) {
        return userService.save(user)
    }

    @GetMapping("/api/v2/users/{userId}/continueWatching")
    fun getContinueWatching(
        @PathVariable("userId") userId: String
    ): List<MovieDto> {
        return userService.getContinueWatching(userId)
    }

    @GetMapping("/api/v2/users/{userId}/watched")
    fun watched(
        @PathVariable("userId") userId: String
    ): List<MovieDto> {
        return userService.getWatched(userId)
    }

    @GetMapping("/api/v2/users/{userId}/toWatch")
    fun toWatch(
        @PathVariable("userId") userId: String
    ): List<MovieDto> {
        return userService.getToWatch(userId)
    }

    @PostMapping("/api/v2/users/{userId}/continueWatching")
    @ResponseStatus(HttpStatus.CREATED)
    fun addToContinueWatching(
        @PathVariable("userId") userId: String,
        @Valid @RequestBody movie: MovieDocument
    ) {
        return userService.saveContinueWatching(userId, movie)
    }

    @PostMapping("/api/v2/users/{userId}/watched")
    @ResponseStatus(HttpStatus.CREATED)
    fun addToWatched(
        @PathVariable("userId") userId: String,
        @Valid @RequestBody movie: MovieDocument
    ) {
        return userService.saveWatched(userId, movie)
    }

    @PostMapping("/api/v2/users/{userId}/toWatch")
    @ResponseStatus(HttpStatus.CREATED)
    fun addToToWatch(
        @PathVariable("userId") userId: String,
        @Valid @RequestBody movie: MovieDocument
    ) {
        return userService.saveToWatch(userId, movie)
    }
}