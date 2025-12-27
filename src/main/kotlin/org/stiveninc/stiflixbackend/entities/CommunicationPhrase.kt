package org.stiveninc.stiflixbackend.entities

import kotlinx.serialization.Serializable
import org.stiveninc.stiflixbackend.enums.CommunicationType

@Serializable
data class CommunicationPhrase(
    val type: CommunicationType? = null,
    val content: String? = null,
)
