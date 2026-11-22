package com.viorayls.app.model

data class Track(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String?,
    val artworkUrl: String?,
    val previewUrl: String?,
    val durationMs: Long,
    val genre: String? = null,
    val trackNumber: Int = 0,
    val isFavorite: Boolean = false
) {
    fun durationSeconds(): Double = durationMs / 1000.0
    fun durationFormatted(): String {
        val totalSeconds = durationMs / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }
}
