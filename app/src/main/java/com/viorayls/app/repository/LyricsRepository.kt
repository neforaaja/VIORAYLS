package com.viorayls.app.repository

import com.viorayls.app.api.ApiClient
import com.viorayls.app.api.model.LrclibRecord
import com.viorayls.app.model.LyricLine
import com.viorayls.app.model.LyricsResult
import retrofit2.HttpException
import retrofit2.Response

class LyricsRepository {

    suspend fun fetchLyrics(
        title: String,
        artist: String,
        album: String?,
        durationMs: Long
    ): LyricsResult {
        return try {
            // Step 1: exact match with duration tolerance
            var response: Response<LrclibRecord>? = ApiClient.lrclibService.get(
                trackName = title,
                artistName = artist,
                albumName = album,
                duration = (durationMs / 1000).toInt()
            )
            var record = response.body

            // Step 2: exact match without duration
            if (record == null || response?.isSuccessful == false) {
                response = ApiClient.lrclibService.get(
                    trackName = title,
                    artistName = artist,
                    albumName = null,
                    duration = null
                )
                record = response.body
            }

            // Step 3: broad search fallback
            if ((record == null || response?.isSuccessful == false) && title.isNotEmpty()) {
                val searchResponse = ApiClient.lrclibService.search(q = "$title $artist")
                val list: List<LrclibRecord>? = searchResponse.body
                if (list != null && list.isNotEmpty()) {
                    record = list.first()
                }
            }

            if (record == null) {
                LyricsResult.NotFound("Synced lyrics are not available for this track.")
            } else if (record.instrumental) {
                LyricsResult.NotFound("This track is instrumental and has no lyrics.")
            } else {
                val lines = record.toLyricLines()
                if (lines.isEmpty() && record.plainLyrics.isNullOrBlank()) {
                    LyricsResult.NotFound("Lyrics are not available for this track.")
                } else {
                    LyricsResult.Success(lines, record.plainLyrics)
                }
            }
        } catch (e: HttpException) {
            LyricsResult.Error(e)
        } catch (e: Exception) {
            LyricsResult.Error(e)
        }
    }
}
