package org.stiveninc.stiflixbackend.services

import org.springframework.stereotype.Service
import org.stiveninc.stiflixbackend.dtos.TmdbMovieDto
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.springframework.cache.annotation.Cacheable
import org.stiveninc.stiflixbackend.dtos.TmdbGenresResponse
import org.stiveninc.stiflixbackend.dtos.TmdbPagedResponse
import org.stiveninc.stiflixbackend.dtos.TmdbVideosResponse
import org.stiveninc.stiflixbackend.entities.CommunicationPhrase
import org.stiveninc.stiflixbackend.exceptions.PopularMoviesException
import org.stiveninc.stiflixbackend.repositories.Repository

@Service
class MovieServiceImpl(
    private val tmdbReadToken: String = System.getenv("TMDB_READ_TOKEN")
        ?: error("TMDB_READ_TOKEN must be set"),
    private val repository: Repository
) : MovieService {

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    @Cacheable(value = ["tmdb_movies"], key = "'popular_movies'")
    override suspend fun getPopularMovies(): List<TmdbMovieDto> {
        val response = client.get("https://api.themoviedb.org/3/discover/movie") {
            parameter("include_adult", false)
            parameter("include_video", false)
            header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
            header(HttpHeaders.Accept, "application/json")
        }

        if (!response.status.isSuccess()) {
            throw PopularMoviesException("Failed: ${response.status}")
        }

        val body: TmdbPagedResponse<TmdbMovieDto> = response.body()
        return body.results
    }

    @Cacheable(value = ["tmdb_tvshows"], key = "'popular_tvshows'")
    override suspend fun getPopularTvShows(): List<TmdbMovieDto> {
        val response = client.get("https://api.themoviedb.org/3/discover/tv") {
            parameter("include_adult", false)
            parameter("include_video", false)
            parameter("language", "en-US")
            parameter("page", 1)
            parameter("sort_by", "popularity.desc")
            header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
            header(HttpHeaders.Accept, "application/json")
        }

        if (!response.status.isSuccess()) {
            throw PopularMoviesException("Failed: ${response.status}")
        }

        val body: TmdbPagedResponse<TmdbMovieDto> = response.body()
        return body.results
    }

    @Cacheable(value = ["tmdb_movies"], key = "'top_rated_movies'")
    override suspend fun getTopRatedMovies(): List<TmdbMovieDto> {
        val response = client.get("https://api.themoviedb.org/3/movie/top_rated") {
            parameter("include_adult", false)
            parameter("include_null_first_air_dates", false)
            parameter("language", "en-US")
            parameter("page", 1)
            parameter("sort_by", "popularity.desc")
            header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
            header(HttpHeaders.Accept, "application/json")
        }

        if (!response.status.isSuccess()) {
            throw PopularMoviesException("Failed: ${response.status}")
        }

        val body: TmdbPagedResponse<TmdbMovieDto> = response.body()
        return body.results
    }

    @Cacheable(value = ["tmdb_tvshows"], key = "'top_rated_tvshows'")
    override suspend fun getTopRatedTvShows(): List<TmdbMovieDto> {
        val response = client.get("https://api.themoviedb.org/3/tv/top_rated") {
            parameter("include_adult", false)
            parameter("include_null_first_air_dates", false)
            parameter("language", "en-US")
            parameter("page", 1)
            parameter("sort_by", "popularity.desc")
            header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
            header(HttpHeaders.Accept, "application/json")
        }

        if (!response.status.isSuccess()) {
            throw PopularMoviesException("Failed: ${response.status}")
        }

        val body: TmdbPagedResponse<TmdbMovieDto> = response.body()
        return body.results
    }

    @Cacheable(value = ["tmdb_movies"], key = "'trending_movies'")
    override suspend fun getTrendingMovies(): TmdbPagedResponse<TmdbMovieDto> {
        val response = client.get("https://api.themoviedb.org/3/trending/all/day") {
            parameter("language", "en-US")
            header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
            header(HttpHeaders.Accept, "application/json")
        }

        if (!response.status.isSuccess()) {
            throw PopularMoviesException("Failed: ${response.status}")
        }

        return response.body()
    }

    override suspend fun getTvShowDetails(tvShowId: Int): TmdbMovieDto {
        val response = client.get("https://api.themoviedb.org/3/tv/$tvShowId") {
            parameter("language", "en-US")
            header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
            header(HttpHeaders.Accept, "application/json")
        }

        if (!response.status.isSuccess()) {
            throw PopularMoviesException("Failed: ${response.status}")
        }

        return response.body()
    }

    @Cacheable(value = ["tmdb_movies"], key = "'discover_movies'")
    override suspend fun discoverMovies(): List<TmdbMovieDto> {
        val response = client.get("https://api.themoviedb.org/3/discover/movie") {
            parameter("include_adult", false)
            parameter("include_video", false)
            parameter("language", "en-US")
            parameter("page", 1)
            parameter("sort_by", "popularity.desc")
            header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
            header(HttpHeaders.Accept, "application/json")
        }

        if (!response.status.isSuccess()) {
            throw PopularMoviesException("Failed: ${response.status}")
        }

        val body: TmdbPagedResponse<TmdbMovieDto> = response.body()
        return body.results
    }

    @Cacheable(value = ["tmdb_tvshows"], key = "'discover_tvshows'")
    override suspend fun discoverTvShows(): List<TmdbMovieDto> {
        val response = client.get("https://api.themoviedb.org/3/discover/tv") {
            parameter("include_adult", false)
            parameter("include_video", false)
            parameter("include_null_first_air_dates", false)
            parameter("language", "en-US")
            parameter("page", 1)
            parameter("sort_by", "popularity.desc")
            header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
            header(HttpHeaders.Accept, "application/json")
        }

        if (!response.status.isSuccess()) {
            throw PopularMoviesException("Failed: ${response.status}")
        }

        val body: TmdbPagedResponse<TmdbMovieDto> = response.body()
        return body.results
    }

    override suspend fun search(query: String): TmdbPagedResponse<TmdbMovieDto> {
        val response = client.get("https://api.themoviedb.org/3/search/multi") {
            parameter("query", query)
            parameter("include_adult", false)
            parameter("language", "en-US")
            parameter("page", 1)
            header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
            header(HttpHeaders.Accept, "application/json")
        }

        if (!response.status.isSuccess()) {
            throw PopularMoviesException("Failed: ${response.status}")
        }

        return response.body()
    }

    override suspend fun getTrailerKey(
        mediaType: String,
        mediaId: Int
    ): List<String> {
        val response = client.get(
            "https://api.themoviedb.org/3/$mediaType/$mediaId/videos"
        ) {
            parameter("language", "en-US")
            header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
            header(HttpHeaders.Accept, "application/json")
        }

        if (!response.status.isSuccess()) {
            throw PopularMoviesException("TMDB error: ${response.status}")
        }

        val body: TmdbVideosResponse = response.body()
        return body.results
            .filter { it.site == "YouTube" && it.type == "Trailer" }
            .map { it.key }
    }

    override suspend fun mediaGenres(mediaId: Int, mediaType: String): Map<Int, String> {
        val response = client.get(
            "https://api.themoviedb.org/3/$mediaType/$mediaId"
        ) {
            parameter("language", "en-US")
            header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
            header(HttpHeaders.Accept, "application/json")
        }

        if (!response.status.isSuccess()) {
            throw PopularMoviesException("TMDB error: ${response.status}")
        }

        val body: TmdbGenresResponse = response.body()
        return body.genres.associate { it.id to it.name }
    }

    override suspend fun mediaDetails(
        mediaType: String,
        mediaId: Int
    ): String {
        val response = client.get(
            "https://api.themoviedb.org/3/$mediaType/$mediaId"
        ) {
            parameter("language", "en-US")
            header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
            header(HttpHeaders.Accept, "application/json")
        }

        if (!response.status.isSuccess()) {
            throw PopularMoviesException("TMDB error: ${response.status}")
        }

        return response.bodyAsText()
    }

    override suspend fun getTvShowsSeasons(tvShowId: Int, seasonNumber: Int): String {
        val response = client.get("https://api.themoviedb.org/3/tv/$tvShowId/season/$seasonNumber") {
            parameter("language", "en-US")
            header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
            header(HttpHeaders.Accept, "application/json")
        }

        if (!response.status.isSuccess()) {
            throw PopularMoviesException("Failed: ${response.status}")
        }

        return response.bodyAsText()
    }

    override suspend fun getLogos(
        mediaType: String,
        mediaId: Int
    ): String {
        val response = client.get(
            "https://api.themoviedb.org/3/$mediaType/$mediaId"
        ) {
            parameter("language", "en-US")
            parameter("append_to_response", "images")
            parameter("include_image_language", "en")
            header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
            header(HttpHeaders.Accept, "application/json")
        }

        if (!response.status.isSuccess()) {
            throw PopularMoviesException("TMDB error: ${response.status}")
        }

        return response.bodyAsText()
    }

    override suspend fun getStiflixChillHome(page: Int): TmdbPagedResponse<TmdbMovieDto> {
        val response = client.get("https://api.themoviedb.org/3/discover/movie") {
            parameter("page", page)
            parameter("include_video", false)
            parameter("language", "en-US")
            parameter("sort_by", "popularity.desc")
            parameter("with_genres", "35|10749")
            header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
            header(HttpHeaders.Accept, "application/json")
        }

        if (!response.status.isSuccess()) {
            throw PopularMoviesException("TMDB error: ${response.status}")
        }

        return response.body()
    }

    override suspend fun getStiflixChillCommunication(): List<CommunicationPhrase> {
        return repository.getCommunicationPhrases()
    }

    override suspend fun saveStiflixChillCommunication(communicationPhrase: CommunicationPhrase): Boolean {
        return repository.saveCommunicationPhrase(communicationPhrase)
    }

    override suspend fun updateStiflixChillCommunication(communicationPhrase: CommunicationPhrase): Boolean {
        return repository.updateCommunicationPhrase(communicationPhrase)
    }

    override suspend fun deleteStiflixChillCommunication(id: String): Boolean {
        return repository.deleteCommunicationPhrase(id)
    }
}