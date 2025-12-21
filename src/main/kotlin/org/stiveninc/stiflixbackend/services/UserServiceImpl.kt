package org.stiveninc.stiflixbackend.services

import org.springframework.stereotype.Service
import org.stiveninc.stiflixbackend.dtos.UserDto
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
}