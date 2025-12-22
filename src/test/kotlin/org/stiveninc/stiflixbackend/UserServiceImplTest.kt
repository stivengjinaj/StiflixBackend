package org.stiveninc.stiflixbackend

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.stiveninc.stiflixbackend.dtos.MovieDto
import org.stiveninc.stiflixbackend.dtos.UserDto
import org.stiveninc.stiflixbackend.entities.MovieDocument
import org.stiveninc.stiflixbackend.entities.UserDocument
import org.stiveninc.stiflixbackend.repositories.UserRepository
import org.stiveninc.stiflixbackend.services.UserService
import org.stiveninc.stiflixbackend.services.UserServiceImpl

@ExtendWith(MockitoExtension::class)
class UserServiceImplTest {

    private lateinit var userRepository: UserRepository
    private lateinit var userService: UserService

    private val testUserId = "user123"
    private val testEmail = "test@example.com"

    @BeforeEach
    fun setup() {
        userRepository = mockk()
        userService = UserServiceImpl(userRepository)
    }

    @Test
    fun `getUserById should return user dto`() {
        val expectedUser = UserDto(
            id = testUserId,
            fullName = "Test User",
            email = testEmail,
            avatar = "avatar-url",
            verified = true
        )
        every { userRepository.findById(testUserId) } returns expectedUser

        val result = userService.getUserById(testUserId)

        assertEquals(expectedUser, result)
        verify(exactly = 1) { userRepository.findById(testUserId) }
    }

    @Test
    fun `getUserByEmail should return user dto`() {
        val expectedUser = UserDto(
            id = testUserId,
            fullName = "Test User",
            email = testEmail,
            avatar = "avatar-url",
            verified = false
        )
        every { userRepository.findByEmail(testEmail) } returns expectedUser

        val result = userService.getUserByEmail(testEmail)

        assertEquals(expectedUser, result)
        verify(exactly = 1) { userRepository.findByEmail(testEmail) }
    }

    @Test
    fun `updateUserVerification should call repository method`() {
        justRun { userRepository.updateUserVerification(testUserId) }

        userService.updateUserVerification(testUserId)

        verify(exactly = 1) { userRepository.updateUserVerification(testUserId) }
    }

    @Test
    fun `save should trim user fields and call repository`() {
        val userDocument = UserDocument(
            fullName = "  Test User  ",
            email = "  test@example.com  ",
            avatar = "  avatar-url  "
        )
        val expectedCleanUser = UserDocument(
            fullName = "Test User",
            email = "test@example.com",
            avatar = "avatar-url"
        )
        justRun { userRepository.save(testUserId, expectedCleanUser) }

        userService.save(testUserId, userDocument)

        verify(exactly = 1) { userRepository.save(testUserId, expectedCleanUser) }
    }

    @Test
    fun `getContinueWatching should return list of movies`() {
        val movies = listOf(
            MovieDto(userId = "movie1",
                mediaType = "movie",
                movieId = "123",
                date = "2024-01-01T00:00:00Z",
                episode = 1,
                season = 1),
            MovieDto(userId = "movie1",
                mediaType = "movie",
                movieId = "123",
                date = "2024-01-01T00:00:00Z",
                episode = 1,
                season = 1)
        )
        every { userRepository.getContinueWatching(testUserId) } returns movies

        val result = userService.getContinueWatching(testUserId)

        assertEquals(2, result.size)
        assertEquals(movies, result)
        verify(exactly = 1) { userRepository.getContinueWatching(testUserId) }
    }

    @Test
    fun `getWatched should return watched movies`() {
        val movies = listOf(MovieDto(userId = "movie1",
            mediaType = "movie",
            movieId = "123",
            date = "2024-01-01T00:00:00Z",
            episode = 1,
            season = 1,))
        every { userRepository.getWatched(testUserId) } returns movies

        val result = userService.getWatched(testUserId)

        assertEquals(movies, result)
        verify(exactly = 1) { userRepository.getWatched(testUserId) }
    }

    @Test
    fun `getToWatch should return movies to watch`() {
        val movies = listOf(MovieDto(userId = "movie1",
            mediaType = "movie",
            movieId = "123",
            date = "2024-01-01T00:00:00Z",
            episode = 1,
            season = 1,))
        every { userRepository.getToWatch(testUserId) } returns movies

        val result = userService.getToWatch(testUserId)

        assertEquals(movies, result)
        verify(exactly = 1) { userRepository.getToWatch(testUserId) }
    }

    @Test
    fun `saveContinueWatching should call repository method`() {
        val movie = MovieDocument(mediaType = "movie",
            movieId = "123",
            date = "2024-01-01T00:00:00Z",
            episode = 1,
            season = 1,)
        justRun { userRepository.saveContinueWatching(testUserId, movie) }

        userService.saveContinueWatching(testUserId, movie)

        verify(exactly = 1) { userRepository.saveContinueWatching(testUserId, movie) }
    }

    @Test
    fun `saveWatched should call repository method`() {
        val movie = MovieDocument(mediaType = "movie",
            movieId = "123",
            date = "2024-01-01T00:00:00Z",
            episode = 1,
            season = 1,)
        justRun { userRepository.saveWatched(testUserId, movie) }

        userService.saveWatched(testUserId, movie)

        verify(exactly = 1) { userRepository.saveWatched(testUserId, movie) }
    }

    @Test
    fun `saveToWatch should call repository method`() {
        val movie = MovieDocument(mediaType = "movie",
            movieId = "123",
            date = "2024-01-01T00:00:00Z",
            episode = 1,
            season = 1,)
        justRun { userRepository.saveToWatch(testUserId, movie) }

        userService.saveToWatch(testUserId, movie)

        verify(exactly = 1) { userRepository.saveToWatch(testUserId, movie) }
    }
}
