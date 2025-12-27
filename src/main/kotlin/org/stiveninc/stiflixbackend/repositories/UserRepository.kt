package org.stiveninc.stiflixbackend.repositories

import com.google.cloud.firestore.FieldValue
import com.google.cloud.firestore.Firestore
import org.springframework.stereotype.Repository
import org.stiveninc.stiflixbackend.dtos.MovieDto
import org.stiveninc.stiflixbackend.dtos.UserDto
import org.stiveninc.stiflixbackend.dtos.toDto
import org.stiveninc.stiflixbackend.entities.MovieDocument
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

    fun updateUserVerification(userId: String) {
        val userRef = userCollection.document(userId)
        userRef.update("verified", true)
    }

    fun save(id: String, userDocument: UserDocument) {
        val cleanUser = userDocument.copy(
            fullName = userDocument.fullName,
            email = userDocument.email,
            avatar = userDocument.avatar,
            verified = false
        )
        userCollection.document(id).set(cleanUser)
    }

    fun getContinueWatching(userId: String): List<MovieDto> {
        val continueWatchingSnapshot = userCollection
            .document(userId)
            .collection("continueWatching")
            .get()
            .get()
        return continueWatchingSnapshot.documents
            .map { it.toObject(MovieDocument::class.java) }
            .map { it.toDto(userId) }
    }

    fun getFavourites(userId: String): List<MovieDto> {
        val favouriteWatchingSnapshot = userCollection
            .document(userId)
            .collection("favourites")
            .get()
            .get()
        return favouriteWatchingSnapshot.documents
            .map { it.toObject(MovieDocument::class.java) }
            .map { it.toDto(userId) }
    }

    fun getWatchList(userId: String): List<MovieDto> {
        val watchedSnapshot = userCollection
            .document(userId)
            .collection("watchList")
            .get()
            .get()
        return watchedSnapshot.documents
            .map { it.toObject(MovieDocument::class.java) }
            .map { it.toDto(userId) }
    }

    fun getWatchLater(userId: String): List<MovieDto> {
        val toWatchSnapshot = userCollection
            .document(userId)
            .collection("watchLater")
            .get()
            .get()
        return toWatchSnapshot.documents
            .map { it.toObject(MovieDocument::class.java) }
            .map { it.toDto(userId) }
    }

    fun saveContinueWatching(userId: String, movieDocument: MovieDocument) {
        val data = mapOf(
            "movieId" to movieDocument.movieId,
            "mediaType" to movieDocument.mediaType,
            "posterPath" to movieDocument.posterPath,
            "season" to movieDocument.season?.toInt(),
            "episode" to movieDocument.episode?.toInt(),
            "date" to FieldValue.serverTimestamp()
        )
        movieDocument.movieId?.let {
            userCollection
                .document(userId)
                .collection("continueWatching")
                .document(it)
        }?.set(data)
    }

    fun saveWatchList(userId: String, movieDocument: MovieDocument) {
        val data = mapOf(
            "movieId" to movieDocument.movieId,
            "mediaType" to movieDocument.mediaType
        )
        movieDocument.movieId?.let {
            userCollection
                .document(userId)
                .collection("watchList")
                .document(it)
        }?.set(data)
    }

    fun saveWatchLater(userId: String, movieDocument: MovieDocument) {
        val data = mapOf(
            "movieId" to movieDocument.movieId,
            "mediaType" to movieDocument.mediaType
        )
        movieDocument.movieId?.let {
            userCollection
                .document(userId)
                .collection("watchLater")
                .document(it)
        }?.set(data)
    }

    fun saveFavourites(userId: String, movieDocument: MovieDocument) {
        val data = mapOf(
            "movieId" to movieDocument.movieId,
            "mediaType" to movieDocument.mediaType
        )
        movieDocument.movieId?.let {
            userCollection
                .document(userId)
                .collection("favourites")
                .document(it)
        }?.set(data)
    }

    fun removeContinueWatching(userId: String, movieId: String) {
        userCollection
            .document(userId)
            .collection("continueWatching")
            .document(movieId)
            .delete()
    }

    fun removeFavourites(userId: String, movieId: String) {
        userCollection
            .document(userId)
            .collection("favourites")
            .document(movieId)
            .delete()
    }

    fun removeWatchLater(userId: String, movieId: String) {
        userCollection
            .document(userId)
            .collection("watchLater")
            .document(movieId)
            .delete()
    }

    fun removeWatchList(userId: String, movieId: String) {
        userCollection
            .document(userId)
            .collection("watchList")
            .document(movieId)
            .delete()
    }
}