package org.stiveninc.stiflixbackend.services

import org.stiveninc.stiflixbackend.dtos.UserDto
import org.stiveninc.stiflixbackend.entities.UserDocument

interface UserService {
    fun getUserById(userId: String): UserDto
    fun getUserByEmail(email: String): UserDto
    fun save(user: UserDocument)
}