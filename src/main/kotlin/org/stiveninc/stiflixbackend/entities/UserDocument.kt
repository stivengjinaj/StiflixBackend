package org.stiveninc.stiflixbackend.entities

data class UserDocument(
    val avatar: String? = null,
    val email: String? = null,
    val fullName: String? = null,
    val verified: Boolean = false,
)
