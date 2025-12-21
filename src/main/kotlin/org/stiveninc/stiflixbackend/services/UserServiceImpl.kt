package org.stiveninc.stiflixbackend.services

import org.springframework.stereotype.Service
import org.stiveninc.stiflixbackend.dtos.MovieDto
import org.stiveninc.stiflixbackend.dtos.UserDto
import org.stiveninc.stiflixbackend.entities.MovieDocument
import org.stiveninc.stiflixbackend.entities.UserDocument
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

    override fun save(user: UserDocument) {
        userRepository.save(user)
    }
    override fun getContinueWatching(userId: String): List<MovieDto> {
        return userRepository.getContinueWatching(userId)
    }

    override fun getWatched(userId: String): List<MovieDto> {
        return userRepository.getWatched(userId)
    }

    override fun getToWatch(userId: String): List<MovieDto> {
        return userRepository.getToWatch(userId)
    }

    override fun saveContinueWatching(
        userId: String,
        movie: MovieDocument
    ) {
        userRepository.saveContinueWatching(userId, movie)
    }

    override fun saveWatched(userId: String, movie: MovieDocument) {
        userRepository.saveWatched(userId, movie)
    }

    override fun saveToWatch(userId: String, movie: MovieDocument) {
        userRepository.saveToWatch(userId, movie)
    }
}