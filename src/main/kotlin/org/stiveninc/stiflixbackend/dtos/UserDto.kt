package org.stiveninc.stiflixbackend.dtos

import org.stiveninc.stiflixbackend.entities.UserDocument

data class UserDto(
    val id: String,
    val email: String?,
    val fullName: String?,
    val avatar: String?,
    val verified: Boolean,
)

fun UserDocument.toDto(id: String) = UserDto(
    id = id,
    avatar = avatar,
    email = email,
    fullName = fullName,
    verified = verified
)

