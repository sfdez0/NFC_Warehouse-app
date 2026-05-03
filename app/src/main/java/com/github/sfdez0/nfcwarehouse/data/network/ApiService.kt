package com.github.sfdez0.nfcwarehouse.data.network

import com.github.sfdez0.nfcwarehouse.data.model.Location
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * API functions
 */
interface ApiService {
    /**
     * GET /api/v1/locations
     */
    @GET("/api/v1/locations")
    suspend fun getLocations(): List<Location>

    /**
     * GET /api/v1/locations/{id}
     */
    @GET("/api/v1/locations/{id}")
    suspend fun getLocationById(
        @Path("id") id: Long,
    ): Location

    /**
     * Companion object to create the ApiService
     */
    companion object {
        /**
         * Base URL of the API
         */
        private const val BASE_URL = "http://10.0.2.2:8080/"

        /**
         * Create and return the ApiSevice
         *
         * @return [ApiService]
         */
        fun create(): ApiService =
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
    }
}
