package com.viorayls.app.model

sealed class LyricsResult {
    object Loading : LyricsResult()
    data class Success(val lines: List<LyricLine>, val plain: String?) : LyricsResult()
    data class NotFound(val message: String) : LyricsResult()
    data class Error(val exception: Throwable) : LyricsResult()
}
