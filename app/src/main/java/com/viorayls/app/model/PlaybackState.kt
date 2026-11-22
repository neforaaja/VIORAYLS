package com.viorayls.app.model

enum class RepeatMode { OFF, ONE, ALL }

data class PlaybackState(
    val track: Track?,
    val isPlaying: Boolean = false,
    val positionMs: Long = 0,
    val bufferedMs: Long = 0,
    val durationMs: Long = 0,
    val repeatMode: RepeatMode = RepeatMode.OFF,
    val shuffle: Boolean = false
)
