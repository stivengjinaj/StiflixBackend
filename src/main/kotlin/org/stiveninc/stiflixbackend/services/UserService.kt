package org.stiveninc.stiflixbackend.services

import org.stiveninc.stiflixbackend.dtos.MovieDto
import org.stiveninc.stiflixbackend.dtos.UserDto
import org.stiveninc.stiflixbackend.dtos.UserMoviesDto
import org.stiveninc.stiflixbackend.entities.MovieDocument
import org.stiveninc.stiflixbackend.entities.UserDocument

interface UserService {
    fun getUserById(userId: String): UserDto
    fun getUserByEmail(email: String): UserDto
    fun updateUserVerification(userId: String)
    fun save(id: String, user: UserDocument)
    fun getContinueWatching(userId: String): List<MovieDto>
    fun saveContinueWatching(userId: String, movie: MovieDocument)
    fun saveToWatchList(userId: String, movie: MovieDocument)
    fun saveToWatchLater(userId: String, movie: MovieDocument)
    fun saveToFavourites(userId: String, movies: MovieDocument)
    fun removeFromFavourites(userId: String, movieId: String)
    fun removeFromWatchList(userId: String, movieId: String)
    fun removeFromWatchLater(userId: String, movieId: String)
    fun removeFromContinueWatching(userId: String, movieId: String)
    fun getUserMovies(userId: String): UserMoviesDto
}