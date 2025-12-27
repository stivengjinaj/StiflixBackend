package org.stiveninc.stiflixbackend.services

import org.springframework.stereotype.Service
import org.stiveninc.stiflixbackend.config.setUserRole
import org.stiveninc.stiflixbackend.dtos.MovieDto
import org.stiveninc.stiflixbackend.dtos.UserDto
import org.stiveninc.stiflixbackend.dtos.UserMoviesDto
import org.stiveninc.stiflixbackend.entities.MovieDocument
import org.stiveninc.stiflixbackend.entities.UserDocument
import org.stiveninc.stiflixbackend.enums.UserRole
import org.stiveninc.stiflixbackend.repositories.UserRepository

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {
    override fun getUserById(userId: String) : UserDto {
        return userRepository.findById(userId)
    }

    override fun getUserByEmail(email: String): UserDto {
        return userRepository.findByEmail(email)
    }

    override fun updateUserVerification(userId: String) {
        return userRepository.updateUserVerification(userId)
    }

    override fun save(id: String, user: UserDocument) {
        val cleanUser = user.copy(
            fullName = user.fullName?.trim(),
            email = user.email?.trim(),
            avatar = user.avatar?.trim()
        )
        setUserRole(id, UserRole.VIEWER)
        userRepository.save(id, cleanUser)
    }
    override fun getContinueWatching(userId: String): List<MovieDto> {
        return userRepository.getContinueWatching(userId)
    }

    override fun saveContinueWatching(userId: String, movie: MovieDocument) {
        userRepository.saveContinueWatching(userId, movie)
    }

    override fun saveToWatchList(userId: String, movie: MovieDocument) {
        userRepository.saveWatchList(userId, movie)
    }

    override fun saveToWatchLater(userId: String, movie: MovieDocument) {
        userRepository.saveWatchLater(userId, movie)
    }

    override fun saveToFavourites(userId: String, movies: MovieDocument) {
        userRepository.saveFavourites(userId, movies)
    }

    override fun removeFromFavourites(userId: String, movieId: String) {
        userRepository.removeFavourites(userId, movieId)
    }

    override fun removeFromWatchList(userId: String, movieId: String) {
        userRepository.removeWatchList(userId, movieId)
    }

    override fun removeFromWatchLater(userId: String, movieId: String) {
        userRepository.removeWatchLater(userId, movieId)
    }

    override fun removeFromContinueWatching(userId: String, movieId: String) {
        userRepository.removeContinueWatching(userId, movieId)
    }

    override fun getUserMovies(userId: String): UserMoviesDto {
        val favourites = userRepository.getFavourites(userId)
        val watchList = userRepository.getWatchList(userId)
        val watchLater = userRepository.getWatchLater(userId)
        val continueWatching = userRepository.getContinueWatching(userId)

        return UserMoviesDto (
            favourites = favourites,
            watchList = watchList,
            watchLater = watchLater,
            continueWatching = continueWatching,
        )
    }
}