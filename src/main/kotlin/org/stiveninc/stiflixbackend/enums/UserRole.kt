package org.stiveninc.stiflixbackend.enums

import kotlinx.serialization.Serializable

@Serializable
enum class UserRole {
    OWNER,
    EDITOR,
    VIEWER
}