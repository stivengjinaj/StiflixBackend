package org.stiveninc.stiflixbackend

import io.kotest.matchers.shouldBe
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandler
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.stiveninc.stiflixbackend.dtos.*
import org.stiveninc.stiflixbackend.exceptions.PopularMoviesException
import org.stiveninc.stiflixbackend.services.MovieService

class MovieServiceImplTest {

    private fun createMockClient(handler: MockRequestHandler): HttpClient {
        return HttpClient(MockEngine) {
            engine {
                addHandler(handler)
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }

    @Test
    fun `getPopularMovies should return list of movies`() = runTest {
        val mockMovies = listOf(
            TmdbMovieDto(
                id = 1,
                title = "Test Movie 1",
                overview = "Description 1",
                posterPath = "/poster1.jpg",
                backdropPath = "/backdrop1.jpg",
                voteAverage = 8.5,
                releaseDate = "2024-01-01"
            ),
            TmdbMovieDto(
                id = 2,
                title = "Test Movie 2",
                overview = "Description 2",
                posterPath = "/poster2.jpg",
                backdropPath = "/backdrop2.jpg",
                voteAverage = 7.5,
                releaseDate = "2024-02-01"
            )
        )

        val responseJson = """
            {
                "page": 1,
                "results": ${Json.encodeToString(mockMovies)},
                "total_pages": 1,
                "total_results": 2
            }
        """.trimIndent()

        val client = createMockClient { request ->
            when (request.url.encodedPath) {
                "/3/discover/movie" -> {
                    respond(
                        content = responseJson,
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
                else -> respondError(HttpStatusCode.NotFound)
            }
        }

        val service = createServiceWithClient(client)
        val result = service.getPopularMovies()

        result.size shouldBe 2
        result[0].title shouldBe "Test Movie 1"
        result[1].title shouldBe "Test Movie 2"
    }

    @Test
    fun `getPopularMovies should throw exception on API failure`() = runTest {
        val client = createMockClient { respondError(HttpStatusCode.InternalServerError) }
        val service = createServiceWithClient(client)

        assertThrows<PopularMoviesException> {
            service.getPopularMovies()
        }
    }

    @Test
    fun `getPopularTvShows should return list of TV shows`() = runTest {
        val mockTvShows = listOf(
            TmdbMovieDto(
                id = 1,
                name = "Test Show 1",
                overview = "Show description 1",
                posterPath = "/show1.jpg",
                voteAverage = 9.0,
                firstAirDate = "2024-01-01"
            )
        )

        val responseJson = """
            {
                "page": 1,
                "results": ${Json.encodeToString(mockTvShows)},
                "total_pages": 1,
                "total_results": 1
            }
        """.trimIndent()

        val client = createMockClient { request ->
            when (request.url.encodedPath) {
                "/3/discover/tv" -> respond(content = responseJson, status = HttpStatusCode.OK, headers = headersOf(HttpHeaders.ContentType, "application/json"))
                else -> respondError(HttpStatusCode.NotFound)
            }
        }

        val service = createServiceWithClient(client)
        val result = service.getPopularTvShows()

        result.size shouldBe 1
        result[0].name shouldBe "Test Show 1"
    }

    @Test
    fun `getTopRatedMovies should return top rated movies`() = runTest {
        val mockMovies = listOf(
            TmdbMovieDto(id = 1, title = "Top Rated Movie", overview = "Great movie", voteAverage = 9.5)
        )

        val responseJson = """
            {
                "page": 1,
                "results": ${Json.encodeToString(mockMovies)},
                "total_pages": 1,
                "total_results": 1
            }
        """.trimIndent()

        val client = createMockClient { request ->
            when (request.url.encodedPath) {
                "/3/movie/top_rated" -> respond(content = responseJson, status = HttpStatusCode.OK, headers = headersOf(HttpHeaders.ContentType, "application/json"))
                else -> respondError(HttpStatusCode.NotFound)
            }
        }

        val service = createServiceWithClient(client)
        val result = service.getTopRatedMovies()

        result.size shouldBe 1
        result[0].voteAverage shouldBe 9.5
    }

    @Test
    fun `getTopRatedTvShows should return top rated TV shows`() = runTest {
        val mockTvShows = listOf(
            TmdbMovieDto(id = 1, name = "Top TV Show", overview = "Excellent show", voteAverage = 9.8)
        )

        val responseJson = """
            {
                "page": 1,
                "results": ${Json.encodeToString(mockTvShows)},
                "total_pages": 1,
                "total_results": 1
            }
        """.trimIndent()

        val client = createMockClient { request ->
            when (request.url.encodedPath) {
                "/3/tv/top_rated" -> respond(content = responseJson, status = HttpStatusCode.OK, headers = headersOf(HttpHeaders.ContentType, "application/json"))
                else -> respondError(HttpStatusCode.NotFound)
            }
        }

        val service = createServiceWithClient(client)
        val result = service.getTopRatedTvShows()

        result.size shouldBe 1
        result[0].name shouldBe "Top TV Show"
    }

    @Test
    fun `getTrendingMovies should return trending content`() = runTest {
        val mockMovies = listOf(
            TmdbMovieDto(id = 1, title = "Trending Movie", overview = "Hot movie")
        )

        val responseJson = """
            {
                "page": 1,
                "results": ${Json.encodeToString(mockMovies)},
                "total_pages": 1,
                "total_results": 1
            }
        """.trimIndent()

        val client = createMockClient { request ->
            when (request.url.encodedPath) {
                "/3/trending/all/week" -> respond(content = responseJson, status = HttpStatusCode.OK, headers = headersOf(HttpHeaders.ContentType, "application/json"))
                else -> respondError(HttpStatusCode.NotFound)
            }
        }

        val service = createServiceWithClient(client)
        val result = service.getTrendingMovies()

        result.size shouldBe 1
        result[0].title shouldBe "Trending Movie"
    }

    @Test
    fun `getTvShowDetails should return TV show details`() = runTest {
        val tvShowId = 12345
        val mockTvShow = TmdbMovieDto(
            id = tvShowId,
            name = "Detailed Show",
            overview = "Full details",
            numberOfSeasons = 5,
            numberOfEpisodes = 50
        )

        val client = createMockClient { request ->
            when {
                request.url.encodedPath.contains("/3/tv/$tvShowId") -> {
                    respond(
                        content = Json.encodeToString(TmdbMovieDto.serializer(), mockTvShow),
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
                else -> respondError(HttpStatusCode.NotFound)
            }
        }

        val service = createServiceWithClient(client)
        val result = service.getTvShowDetails(tvShowId)

        result.id shouldBe tvShowId
        result.name shouldBe "Detailed Show"
        result.numberOfSeasons shouldBe 5
    }

    @Test
    fun `search should return movies and TV shows separately`() = runTest {
        val mockResults = listOf(
            TmdbMovieDto(id = 1, title = "Movie Result", overview = "A movie"),
            TmdbMovieDto(id = 2, name = "TV Result", overview = "A TV show"),
            TmdbMovieDto(id = 3, title = "Another Movie", overview = "Another movie")
        )

        val responseJson = """
            {
                "page": 1,
                "results": ${Json.encodeToString(mockResults)},
                "total_pages": 1,
                "total_results": 3
            }
        """.trimIndent()

        val client = createMockClient { request ->
            when (request.url.encodedPath) {
                "/3/search/multi" -> respond(content = responseJson, status = HttpStatusCode.OK, headers = headersOf(HttpHeaders.ContentType, "application/json"))
                else -> respondError(HttpStatusCode.NotFound)
            }
        }

        val service = createServiceWithClient(client)
        val (movies, tvShows) = service.search("test query")

        movies.size shouldBe 2
        tvShows.size shouldBe 1
        movies[0].title shouldBe "Movie Result"
        tvShows[0].name shouldBe "TV Result"
    }

    @Test
    fun `getTrailerKey should return YouTube trailer keys`() = runTest {
        val mockVideos = listOf(
            TmdbVideoDto(key = "trailer1", site = "YouTube", type = "Trailer"),
            TmdbVideoDto(key = "teaser1", site = "YouTube", type = "Teaser"),
            TmdbVideoDto(key = "trailer2", site = "YouTube", type = "Trailer")
        )

        val responseJson = """
            {
                "id": 1,
                "results": ${Json.encodeToString(mockVideos)}
            }
        """.trimIndent()

        val client = createMockClient { request ->
            when {
                request.url.encodedPath.contains("/videos") -> respond(content = responseJson, status = HttpStatusCode.OK, headers = headersOf(HttpHeaders.ContentType, "application/json"))
                else -> respondError(HttpStatusCode.NotFound)
            }
        }

        val service = createServiceWithClient(client)
        val result = service.getTrailerKey("movie", 123)

        result.size shouldBe 2
        result shouldBe listOf("trailer1", "trailer2")
    }

    @Test
    fun `mediaGenres should return genre map`() = runTest {
        val mockGenres = listOf(
            TmdbGenreDto(id = 1, name = "Action"),
            TmdbGenreDto(id = 2, name = "Comedy"),
            TmdbGenreDto(id = 3, name = "Drama")
        )

        val responseJson = """
            {
                "genres": ${Json.encodeToString(mockGenres)}
            }
        """.trimIndent()

        val client = createMockClient { request ->
            when {
                request.url.encodedPath.contains("/genre/") -> respond(content = responseJson, status = HttpStatusCode.OK, headers = headersOf(HttpHeaders.ContentType, "application/json"))
                else -> respondError(HttpStatusCode.NotFound)
            }
        }

        val service = createServiceWithClient(client)
        val result = service.mediaGenres("movie")

        result.size shouldBe 3
        result[1] shouldBe "Action"
        result[2] shouldBe "Comedy"
        result[3] shouldBe "Drama"
    }

    @Test
    fun `mediaDetails should return media details`() = runTest {
        val mediaId = 456
        val mockMedia = TmdbMovieDto(
            id = mediaId,
            title = "Media Title",
            overview = "Media overview",
            voteAverage = 8.0
        )

        val client = createMockClient { request ->
            respond(
                content = Json.encodeToString(TmdbMovieDto.serializer(), mockMedia),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val service = createServiceWithClient(client)
        val result = service.mediaDetails("movie", mediaId) as TmdbMovieDto

        result.id shouldBe mediaId
        result.title shouldBe "Media Title"
    }

    @Test
    fun `getLogos should return logo URLs`() = runTest {
        val mockLogos = listOf(
            TmdbLogoDto(filePath = "/logo1.png"),
            TmdbLogoDto(filePath = "/logo2.png"),
            TmdbLogoDto(filePath = "")
        )

        val responseJson = """
            {
                "logos": ${Json.encodeToString(mockLogos)}
            }
        """.trimIndent()

        val client = createMockClient { request ->
            when {
                request.url.encodedPath.contains("/images") -> respond(content = responseJson, status = HttpStatusCode.OK, headers = headersOf(HttpHeaders.ContentType, "application/json"))
                else -> respondError(HttpStatusCode.NotFound)
            }
        }

        val service = createServiceWithClient(client)
        val result = service.getLogos("movie", 123)

        result.size shouldBe 2
        result[0] shouldBe "https://image.tmdb.org/t/p/original/logo1.png"
        result[1] shouldBe "https://image.tmdb.org/t/p/original/logo2.png"
    }

    @Test
    fun `discoverMovies should return discovered movies`() = runTest {
        val mockMovies = listOf(
            TmdbMovieDto(id = 1, title = "Discovered Movie", overview = "Found it")
        )

        val responseJson = """
            {
                "page": 1,
                "results": ${Json.encodeToString(mockMovies)},
                "total_pages": 1,
                "total_results": 1
            }
        """.trimIndent()

        val client = createMockClient { request ->
            when (request.url.encodedPath) {
                "/3/discover/movie" -> respond(content = responseJson, status = HttpStatusCode.OK, headers = headersOf(HttpHeaders.ContentType, "application/json"))
                else -> respondError(HttpStatusCode.NotFound)
            }
        }

        val service = createServiceWithClient(client)
        val result = service.discoverMovies()

        result.size shouldBe 1
        result[0].title shouldBe "Discovered Movie"
    }

    @Test
    fun `discoverTvShows should return discovered TV shows`() = runTest {
        val mockTvShows = listOf(
            TmdbMovieDto(id = 1, name = "Discovered Show", overview = "Found show")
        )

        val responseJson = """
            {
                "page": 1,
                "results": ${Json.encodeToString(mockTvShows)},
                "total_pages": 1,
                "total_results": 1
            }
        """.trimIndent()

        val client = createMockClient { request ->
            when (request.url.encodedPath) {
                "/3/discover/tv" -> respond(content = responseJson, status = HttpStatusCode.OK, headers = headersOf(HttpHeaders.ContentType, "application/json"))
                else -> respondError(HttpStatusCode.NotFound)
            }
        }

        val service = createServiceWithClient(client)
        val result = service.discoverTvShows()

        result.size shouldBe 1
        result[0].name shouldBe "Discovered Show"
    }

    @Test
    fun `getTvShowsSeasons should return seasons details`() = runTest {
        val mockSeasons = """
            {
                "seasons": [
                    {"seasonNumber": 1, "episodeCount": 10}
                ]
            }
        """.trimIndent()

        val client = createMockClient { request ->
            respond(
                content = mockSeasons,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val service = createServiceWithClient(client)
        val result = service.getTvShowsSeasons(1, 1)

        result shouldBe mapOf("seasons" to listOf(mapOf("seasonNumber" to 1.0, "episodeCount" to 10.0)))
    }

    private fun createServiceWithClient(client: HttpClient): MovieService {
        return object : MovieService {
            override suspend fun getPopularMovies(): List<TmdbMovieDto> {
                val response = client.get("/3/discover/movie").body<String>()
                val jsonObject = Json.parseToJsonElement(response).jsonObject
                return Json.decodeFromJsonElement(jsonObject["results"]!!)
            }

            override suspend fun getPopularTvShows(): List<TmdbMovieDto> {
                val response = client.get("/3/discover/tv").body<String>()
                val jsonObject = Json.parseToJsonElement(response).jsonObject
                return Json.decodeFromJsonElement(jsonObject["results"]!!)
            }

            override suspend fun getTopRatedMovies(): List<TmdbMovieDto> {
                val response = client.get("/3/movie/top_rated").body<String>()
                val jsonObject = Json.parseToJsonElement(response).jsonObject
                return Json.decodeFromJsonElement(jsonObject["results"]!!)
            }

            override suspend fun getTopRatedTvShows(): List<TmdbMovieDto> {
                val response = client.get("/3/tv/top_rated").body<String>()
                val jsonObject = Json.parseToJsonElement(response).jsonObject
                return Json.decodeFromJsonElement(jsonObject["results"]!!)
            }

            override suspend fun getTrendingMovies(): List<TmdbMovieDto> {
                val response = client.get("/3/trending/all/week").body<String>()
                val jsonObject = Json.parseToJsonElement(response).jsonObject
                return Json.decodeFromJsonElement(jsonObject["results"]!!)
            }

            override suspend fun getTvShowDetails(tvShowId: Int): TmdbMovieDto {
                return client.get("/3/tv/$tvShowId").body()
            }

            override suspend fun discoverMovies(): List<TmdbMovieDto> {
                val response = client.get("/3/discover/movie").body<String>()
                val jsonObject = Json.parseToJsonElement(response).jsonObject
                return Json.decodeFromJsonElement(jsonObject["results"]!!)
            }

            override suspend fun discoverTvShows(): List<TmdbMovieDto> {
                val response = client.get("/3/discover/tv").body<String>()
                val jsonObject = Json.parseToJsonElement(response).jsonObject
                return Json.decodeFromJsonElement(jsonObject["results"]!!)
            }

            override suspend fun search(query: String): Pair<List<TmdbMovieDto>, List<TmdbMovieDto>> {
                val response = client.get("/3/search/multi?query=$query").body<String>()
                val jsonObject = Json.parseToJsonElement(response).jsonObject
                val results: List<TmdbMovieDto> = Json.decodeFromJsonElement(jsonObject["results"]!!)
                val movies = results.filter { it.title != null }
                val tvShows = results.filter { it.name != null }
                return Pair(movies, tvShows)
            }

            override suspend fun getTrailerKey(mediaType: String, mediaId: Int): List<String> {
                val response = client.get("/3/$mediaType/$mediaId/videos").body<String>()
                val jsonObject = Json.parseToJsonElement(response).jsonObject
                val videos: List<TmdbVideoDto> = Json.decodeFromJsonElement(jsonObject["results"]!!)
                return videos.filter { it.type == "Trailer" }.map { it.key }
            }

            override suspend fun mediaGenres(mediaType: String): Map<Int, String> {
                val response = client.get("/3/genre/$mediaType/list").body<String>()
                val jsonObject = Json.parseToJsonElement(response).jsonObject
                val genres: List<TmdbGenreDto> = Json.decodeFromJsonElement(jsonObject["genres"]!!)
                return genres.associate { it.id to it.name }
            }

            override suspend fun mediaDetails(mediaType: String, mediaId: Int): Any {
                return client.get("/3/$mediaType/$mediaId").body<TmdbMovieDto>()
            }

            override suspend fun getTvShowsSeasons(tvShowId: Int, seasonNumber: Int): Any {
                val response = client.get("/3/tv/$tvShowId/season/$seasonNumber").body<String>()
                return Json.parseToJsonElement(response).jsonObject.toMap()
            }

            override suspend fun getLogos(mediaType: String, mediaId: Int): List<String> {
                val response = client.get("/3/$mediaType/$mediaId/images").body<String>()
                val jsonObject = Json.parseToJsonElement(response).jsonObject
                val logos: List<TmdbLogoDto> = Json.decodeFromJsonElement(jsonObject["logos"]!!)
                return logos.filter { it.filePath.isNotEmpty() }
                    .map { "https://image.tmdb.org/t/p/original${it.filePath}" }
            }
        }
    }
}