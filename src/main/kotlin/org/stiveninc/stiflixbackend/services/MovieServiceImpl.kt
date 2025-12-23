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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.springframework.cache.annotation.Cacheable
import org.stiveninc.stiflixbackend.dtos.TmdbGenresResponse
import org.stiveninc.stiflixbackend.dtos.TmdbPagedResponse
import org.stiveninc.stiflixbackend.dtos.TmdbVideosResponse
import org.stiveninc.stiflixbackend.exceptions.PopularMoviesException

@Service
class MovieServiceImpl(
    private val tmdbApiKey: String = System.getenv("TMDB_API_KEY")
        ?: error("TMDB_API_KEY must be set"),
    private val tmdbReadToken: String = System.getenv("TMDB_READ_TOKEN")
        ?: error("TMDB_READ_TOKEN must be set")
): MovieService {

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    @Cacheable(value = ["tmdb_movies"], key = "'popular_movies'")
    override suspend fun getPopularMovies(): List<TmdbMovieDto> =
        withContext(Dispatchers.IO) {
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

            body.results
        }

    @Cacheable(value = ["tmdb_tvshows"], key = "'popular_tvshows'")
    override suspend fun getPopularTvShows(): List<TmdbMovieDto> =
        withContext(Dispatchers.IO) {
            val response = client.get("https://api.themoviedb.org/3/discover/tv") {
                parameter("include_adult", false)
                parameter("include_video", false)
                parameter("language", "en-US")
                parameter("page", 1)
                parameter("sort_by", "popularity.desc")
                parameter("api_key", tmdbApiKey)

                header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
                header(HttpHeaders.Accept, "application/json")
            }

            if (!response.status.isSuccess()) {
                throw PopularMoviesException("Failed: ${response.status}")
            }

            val body: TmdbPagedResponse<TmdbMovieDto> = response.body()

            body.results
        }

    @Cacheable(value = ["tmdb_movies"], key = "'top_rated_movies'")
    override suspend fun getTopRatedMovies(): List<TmdbMovieDto> =
        withContext(Dispatchers.IO) {
            val response = client.get("https://api.themoviedb.org/3/movie/top_rated") {
                parameter("include_adult", false)
                parameter("include_null_first_air_dates", false)
                parameter("language", "en-US")
                parameter("page", 1)
                parameter("sort_by", "popularity.desc")
                parameter("api_key", tmdbApiKey)

                header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
                header(HttpHeaders.Accept, "application/json")
            }

            if (!response.status.isSuccess()) {
                throw PopularMoviesException("Failed: ${response.status}")
            }

            val body: TmdbPagedResponse<TmdbMovieDto> = response.body()

            body.results
        }

    @Cacheable(value = ["tmdb_tvshows"], key = "'top_rated_tvshows'")
    override suspend fun getTopRatedTvShows(): List<TmdbMovieDto> =
        withContext(Dispatchers.IO) {
            val response = client.get("https://api.themoviedb.org/3/tv/top_rated") {
                parameter("include_adult", false)
                parameter("include_null_first_air_dates", false)
                parameter("language", "en-US")
                parameter("page", 1)
                parameter("sort_by", "popularity.desc")
                parameter("api_key", tmdbApiKey)

                header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
                header(HttpHeaders.Accept, "application/json")
            }

            if (!response.status.isSuccess()) {
                throw PopularMoviesException("Failed: ${response.status}")
            }

            val body: TmdbPagedResponse<TmdbMovieDto> = response.body()

            body.results
        }
    @Cacheable(value = ["tmdb_movies"], key = "'trending_movies'")
    override suspend fun getTrendingMovies(): TmdbPagedResponse<TmdbMovieDto> =
        withContext(Dispatchers.IO) {
            val response = client.get("https://api.themoviedb.org/3/trending/all/day") {
                parameter("language", "en-US")
                parameter("api_key", tmdbApiKey)

                header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
                header(HttpHeaders.Accept, "application/json")
            }

            if (!response.status.isSuccess()) {
                throw PopularMoviesException("Failed: ${response.status}")
            }

            val body: TmdbPagedResponse<TmdbMovieDto> = response.body()

            body
        }

    override suspend fun getTvShowDetails(tvShowId: Int): TmdbMovieDto =
        withContext(Dispatchers.IO) {
            val response = client.get("https://api.themoviedb.org/3/tv/$tvShowId") {
                parameter("language", "en-US")
                parameter("api_key", tmdbApiKey)

                header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
                header(HttpHeaders.Accept, "application/json")
            }

            if (!response.status.isSuccess()) {
                throw PopularMoviesException("Failed: ${response.status}")
            }

            val body: TmdbMovieDto = response.body()

            body
        }

    @Cacheable(value = ["tmdb_movies"], key = "'discover_movies'")
    override suspend fun discoverMovies(): List<TmdbMovieDto> =
        withContext(Dispatchers.IO) {
            val response = client.get("https://api.themoviedb.org/3/discover/movie") {
                parameter("include_adult", false)
                parameter("include_video", false)
                parameter("language", "en-US")
                parameter("page", 1)
                parameter("sort_by", "popularity.desc")
                parameter("api_key", tmdbApiKey)

                header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
                header(HttpHeaders.Accept, "application/json")
            }

            if (!response.status.isSuccess()) {
                throw PopularMoviesException("Failed: ${response.status}")
            }

            val body: TmdbPagedResponse<TmdbMovieDto> = response.body()

            body.results
        }

    @Cacheable(value = ["tmdb_tvshows"], key = "'discover_tvshows'")
    override suspend fun discoverTvShows(): List<TmdbMovieDto> =
        withContext(Dispatchers.IO) {
            val response = client.get("https://api.themoviedb.org/3/discover/tv") {
                parameter("include_adult", false)
                parameter("include_video", false)
                parameter("include_null_first_air_dates", false)
                parameter("language", "en-US")
                parameter("page", 1)
                parameter("sort_by", "popularity.desc")
                parameter("api_key", tmdbApiKey)

                header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
                header(HttpHeaders.Accept, "application/json")
            }

            if (!response.status.isSuccess()) {
                throw PopularMoviesException("Failed: ${response.status}")
            }

            val body: TmdbPagedResponse<TmdbMovieDto> = response.body()

            body.results
        }

    override suspend fun search(query: String): TmdbPagedResponse<TmdbMovieDto> =
        withContext(Dispatchers.IO) {
            val response = client.get("https://api.themoviedb.org/3/search/multi") {
                parameter("query", query)
                parameter("include_adult", false)
                parameter("language", "en-US")
                parameter("page", 1)
                parameter("api_key", tmdbApiKey)

                header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
                header(HttpHeaders.Accept, "application/json")
            }

            if (!response.status.isSuccess()) {
                throw PopularMoviesException("Failed: ${response.status}")
            }

            val body: TmdbPagedResponse<TmdbMovieDto> = response.body()

            body
        }

    override suspend fun getTrailerKey(
        mediaType: String,
        mediaId: Int
    ): List<String> =
        withContext(Dispatchers.IO) {

            val response = client.get(
                "https://api.themoviedb.org/3/$mediaType/$mediaId/videos"
            ) {
                parameter("language", "en-US")
                parameter("api_key", tmdbApiKey)
                header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
                header(HttpHeaders.Accept, "application/json")
            }

            if (!response.status.isSuccess()) {
                throw PopularMoviesException("TMDB error: ${response.status}")
            }

            val body: TmdbVideosResponse = response.body()

            body.results
                .filter { it.site == "YouTube" && it.type == "Trailer" }
                .map { it.key }
        }


    override suspend fun mediaGenres(mediaId: Int, mediaType: String): Map<Int, String> =
        withContext(Dispatchers.IO) {

            val response = client.get(
                "https://api.themoviedb.org/3/$mediaType/$mediaId"
            ) {
                parameter("language", "en-US")
                parameter("api_key", tmdbApiKey)

                header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
                header(HttpHeaders.Accept, "application/json")
            }

            if (!response.status.isSuccess()) {
                throw PopularMoviesException("TMDB error: ${response.status}")
            }

            val body: TmdbGenresResponse = response.body()

            body.genres.associate { it.id to it.name }
        }


    override suspend fun mediaDetails(
        mediaType: String,
        mediaId: Int
    ): String =
        withContext(Dispatchers.IO) {

            val response = client.get(
                "https://api.themoviedb.org/3/$mediaType/$mediaId"
            ) {
                parameter("language", "en-US")
                parameter("api_key", tmdbApiKey)
                header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
                header(HttpHeaders.Accept, "application/json")
            }

            if (!response.status.isSuccess()) {
                throw PopularMoviesException("TMDB error: ${response.status}")
            }

            response.bodyAsText()
        }


    override suspend fun getTvShowsSeasons(tvShowId: Int, seasonNumber: Int): String =
        withContext(Dispatchers.IO) {
            val response = client.get("https://api.themoviedb.org/3/tv/$tvShowId/season/$seasonNumber") {
                parameter("language", "en-US")
                parameter("api_key", tmdbApiKey)

                header(HttpHeaders.Authorization, "Bearer $tmdbReadToken")
                header(HttpHeaders.Accept, "application/json")
            }

            if (!response.status.isSuccess()) {
                throw PopularMoviesException("Failed: ${response.status}")
            }

            response.bodyAsText()
        }

    override suspend fun getLogos(
        mediaType: String,
        mediaId: Int
    ): String =
        withContext(Dispatchers.IO) {

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

            response.bodyAsText()
        }
}