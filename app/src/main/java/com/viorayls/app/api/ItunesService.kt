package com.viorayls.app.api

import com.viorayls.app.api.model.ItunesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesService {
    @GET("search")
    suspend fun search(
        @Query("term") term: String,
        @Query("entity") entity: String = "song",
        @Query("limit") limit: Int = 25,
        @Query("country") country: String = "US",
        @Query("media") media: String = "music"
    ): retrofit2.Response<ItunesResponse>
}
