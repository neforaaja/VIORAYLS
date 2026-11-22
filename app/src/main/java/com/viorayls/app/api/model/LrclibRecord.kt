package com.viorayls.app.api.model

import com.google.gson.annotations.SerializedName
import com.viorayls.app.model.LyricLine

data class LrclibRecord(
    @SerializedName("id") val id: Int,
    @SerializedName("trackName") val trackName: String,
    @SerializedName("artistName") val artistName: String,
    @SerializedName("albumName") val albumName: String?,
    @SerializedName("duration") val duration: Double?,
    @SerializedName("instrumental") val instrumental: Boolean,
    @SerializedName("plainLyrics") val plainLyrics: String?,
    @SerializedName("syncedLyrics") val syncedLyrics: String?
) {
    fun toLyricLines(): List<LyricLine> {
        val lines = mutableListOf<LyricLine>()
        syncedLyrics?.let { lrc ->
            lrc.lines().forEach { line ->
                val trimmed = line.trim()
                if (trimmed.isNotEmpty() && trimmed.startsWith("[")) {
                    val endBracket = trimmed.indexOf(']')
                    if (endBracket > 0) {
                        val tag = trimmed.substring(1, endBracket)
                        val text = trimmed.substring(endBracket + 1).trim()
                        val timeMs = parseLrcTime(tag)
                        lines.add(LyricLine(timeMs, text))
                    }
                }
            }
        }
        return lines
    }

    private fun parseLrcTime(tag: String): Long {
        // [mm:ss.xx] or [mm:ss.x]
        val parts = tag.split(":")
        if (parts.size < 2) return 0L
        val minutes = parts[0].toLongOrNull() ?: 0L
        val secPart = parts[1]
        val secSec = secPart.split(".")
        val seconds = secSec[0].toLongOrNull() ?: 0L
        val millis = secSec.getOrNull(1)?.toString()?.toLongOrNull() ?: 0L
        return (minutes * 60 + seconds) * 1000 + millis
    }
}
