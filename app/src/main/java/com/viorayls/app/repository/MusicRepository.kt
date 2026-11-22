package com.viorayls.app.repository

import android.content.Context
import com.viorayls.app.api.ApiClient
import com.viorayls.app.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class MusicRepository(private val context: Context) {
    suspend fun search(term: String): List<Track> {
        return try {
            val response = ApiClient.itunesService.search(term)
            if (!response.isSuccessful) throw HttpException(response)
            response.body?.toTracks() ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun recentTracksFlow(): Flow<List<Track>> = flow {
        emit(emptyList())
    }
}
