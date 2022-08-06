package com.example.gmzucolo.imdbconsultingapi.model

import retrofit2.http.GET

interface ApiService {

    @GET("API/MostPopularMovies/k_9v5jw2c1")
    suspend fun getMostPopularMovies(): MovieResponse
}
