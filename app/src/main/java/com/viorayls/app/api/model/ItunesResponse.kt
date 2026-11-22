package com.viorayls.app.api.model

import com.google.gson.annotations.SerializedName
import com.viorayls.app.model.Track

data class ItunesResponse(
    @SerializedName("resultCount") val resultCount: Int,
    @SerializedName("results") val results: List<ItunesTrack>
) {
    fun toTracks(): List<Track> = results.map { it.toTrack() }
}

data class ItunesTrack(
    @SerializedName("trackId") val trackId: Long,
    @SerializedName("trackName") val trackName: String,
    @SerializedName("artistName") val artistName: String,
    @SerializedName("collectionName") val collectionName: String?,
    @SerializedName("previewUrl") val previewUrl: String?,
    @SerializedName("artworkUrl100") val artworkUrl100: String?,
    @SerializedName("trackTimeMillis") val trackTimeMillis: Long,
    @SerializedName("primaryGenreName") val primaryGenreName: String?,
    @SerializedName("trackNumber") val trackNumber: Int,
    @SerializedName("discNumber") val discNumber: Int
) {
    fun toTrack(): Track =
        Track(
            id = trackId,
            title = trackName,
            artist = artistName,
            album = collectionName,
            artworkUrl = artworkUrl100,
            previewUrl = previewUrl,
            durationMs = trackTimeMillis,
            genre = primaryGenreName,
            trackNumber = trackNumber
        )
}
