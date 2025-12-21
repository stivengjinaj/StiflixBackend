package org.stiveninc.stiflixbackend.services

import org.stiveninc.stiflixbackend.dtos.MovieDto
import org.stiveninc.stiflixbackend.dtos.UserDto
import org.stiveninc.stiflixbackend.entities.MovieDocument
import org.stiveninc.stiflixbackend.entities.UserDocument

interface UserService {
    fun getUserById(userId: String): UserDto
    fun getUserByEmail(email: String): UserDto
    fun save(user: UserDocument)
    fun getContinueWatching(userId: String): List<MovieDto>
    fun getWatched(userId: String): List<MovieDto>
    fun getToWatch(userId: String): List<MovieDto>
    fun saveContinueWatching(userId: String, movie: MovieDocument)
    fun saveWatched(userId: String, movie: MovieDocument)
    fun saveToWatch(userId: String, movie: MovieDocument)
}