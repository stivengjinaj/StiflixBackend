package org.stiveninc.stiflixbackend.services

import org.springframework.stereotype.Service
import org.stiveninc.stiflixbackend.config.setUserRole
import org.stiveninc.stiflixbackend.dtos.MovieDto
import org.stiveninc.stiflixbackend.dtos.UserDto
import org.stiveninc.stiflixbackend.dtos.UserMoviesDto
import org.stiveninc.stiflixbackend.entities.MovieDocument
import org.stiveninc.stiflixbackend.entities.UserDocument
import org.stiveninc.stiflixbackend.enums.UserRole
import org.stiveninc.stiflixbackend.repositories.Repository

@Service
class UserServiceImpl(
    private val repository: Repository
): UserService {
    override fun getUserById(userId: String) : UserDto {
        return repository.findById(userId)
    }

    override fun getUserByEmail(email: String): UserDto {
        return repository.findByEmail(email)
    }

    override fun updateUserVerification(userId: String) {
        return repository.updateUserVerification(userId)
    }

    override fun save(id: String, user: UserDocument) {
        val cleanUser = user.copy(
            fullName = user.fullName?.trim(),
            email = user.email?.trim(),
            avatar = user.avatar?.trim()
        )
        setUserRole(id, UserRole.VIEWER)
        repository.save(id, cleanUser)
    }
    override fun getContinueWatching(userId: String): List<MovieDto> {
        return repository.getContinueWatching(userId)
    }

    override fun saveContinueWatching(userId: String, movie: MovieDocument) {
        repository.saveContinueWatching(userId, movie)
    }

    override fun saveToWatchList(userId: String, movie: MovieDocument) {
        repository.saveWatchList(userId, movie)
    }

    override fun saveToWatchLater(userId: String, movie: MovieDocument) {
        repository.saveWatchLater(userId, movie)
    }

    override fun saveToFavourites(userId: String, movies: MovieDocument) {
        repository.saveFavourites(userId, movies)
    }

    override fun removeFromFavourites(userId: String, movieId: String) {
        repository.removeFavourites(userId, movieId)
    }

    override fun removeFromWatchList(userId: String, movieId: String) {
        repository.removeWatchList(userId, movieId)
    }

    override fun removeFromWatchLater(userId: String, movieId: String) {
        repository.removeWatchLater(userId, movieId)
    }

    override fun removeFromContinueWatching(userId: String, movieId: String) {
        repository.removeContinueWatching(userId, movieId)
    }

    override fun getUserMovies(userId: String): UserMoviesDto {
        val favourites = repository.getFavourites(userId)
        val watchList = repository.getWatchList(userId)
        val watchLater = repository.getWatchLater(userId)
        val continueWatching = repository.getContinueWatching(userId)

        return UserMoviesDto (
            favourites = favourites,
            watchList = watchList,
            watchLater = watchLater,
            continueWatching = continueWatching,
        )
    }
}