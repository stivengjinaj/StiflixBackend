package org.stiveninc.stiflixbackend.repositories

import com.google.cloud.firestore.Firestore
import org.springframework.stereotype.Repository
import org.stiveninc.stiflixbackend.dtos.UserDto
import org.stiveninc.stiflixbackend.dtos.toDto
import org.stiveninc.stiflixbackend.entities.UserDocument
import org.stiveninc.stiflixbackend.exceptions.UserNotFoundException

@Repository
class UserRepository(
    firestore: Firestore
) {
    private val userCollection = firestore.collection("users")

    fun findById(id: String): UserDto {
        val documentSnapshot = userCollection.document(id).get().get()
        return documentSnapshot.toObject(UserDocument::class.java)?.toDto(id)
            ?: throw UserNotFoundException("User with id $id not found")
    }

    fun findByEmail(email: String): UserDto {
        val querySnapshot = userCollection.whereEqualTo("email", email).get().get()
        val documentSnapshot = querySnapshot.documents.firstOrNull()
            ?: throw UserNotFoundException("User with email $email not found")
        return documentSnapshot.toObject(UserDocument::class.java).toDto(documentSnapshot.id)
    }

    fun save(userDocument: UserDocument) {
        userCollection.document().set(userDocument)
    }
}