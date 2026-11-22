package com.viorayls.app.api

import com.viorayls.app.model.LrclibRecord
import retrofit2.http.GET
import retrofit2.http.Query

interface LrclibService {
    @GET("/api/get")
    suspend fun get(
        @Query("track_name") trackName: String,
        @Query("artist_name") artistName: String,
        @Query("album_name") albumName: String?,
        @Query("duration") duration: Int?
    ): retrofit2.Response<LrclibRecord>

    @GET("/api/search")
    suspend fun search(
        @Query("q") q: String
    ): retrofit2.Response<List<LrclibRecord>>
}
