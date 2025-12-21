package org.stiveninc.stiflixbackend.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.stiveninc.stiflixbackend.dtos.UserDto
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
}