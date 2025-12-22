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
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import org.stiveninc.stiflixbackend.dtos.TmdbPagedResponse
import org.stiveninc.stiflixbackend.exceptions.PopularMoviesException

@Service
class MovieServiceImpl(
    private val tmdbApiKey: String = System.getenv("TMDB_API_KEY")
        ?: error("TMDB_API_KEY must be set"),
    private val tmdbReadToken: String = System.getenv("TMDB_READ_TOKEN")
        ?: error("TMDB_READ_TOKEN must be set")
): MovieService {

    private val logger = LoggerFactory.getLogger(MovieServiceImpl::class.java)

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

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


    override fun getPopularTvShows(): List<TmdbMovieDto> {
        TODO("Not yet implemented")
    }

    override fun getTopRatedMovies(): List<TmdbMovieDto> {
        TODO("Not yet implemented")
    }

    override fun getTopRatedTvShows(): List<TmdbMovieDto> {
        TODO("Not yet implemented")
    }

    override fun getTrendingMovies(): List<TmdbMovieDto> {
        TODO("Not yet implemented")
    }

    override fun getTvShowDetails(tvShowId: Int): TmdbMovieDto {
        TODO("Not yet implemented")
    }

    override fun discoverMovies(): List<TmdbMovieDto> {
        TODO("Not yet implemented")
    }

    override fun discoverTvShows(): List<TmdbMovieDto> {
        TODO("Not yet implemented")
    }

    override fun search(query: String): Pair<List<TmdbMovieDto>, List<TmdbMovieDto>> {
        TODO("Not yet implemented")
    }

    override fun getTrailerKey(mediaType: String, mediaId: Int): String? {
        TODO("Not yet implemented")
    }

    override fun mediaGenres(mediaType: String): Map<Int, String> {
        TODO("Not yet implemented")
    }

    override fun mediaDetails(mediaType: String, mediaId: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getTvShowsSeasons(tvShowId: Int, seasonNumber: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getLogos(): List<String> {
        TODO("Not yet implemented")
    }
}