package com.viorayls.app.lyrics

import com.viorayls.app.model.LyricLine

object LyricsSync {
    fun currentLineIndex(lines: List<LyricLine>, positionMs: Long): Int {
        var current = -1
        for (i in lines.indices) {
            if (lines[i].timeMs <= positionMs) current = i else break
        }
        return current
    }

    fun lineAt(lines: List<LyricLine>, positionMs: Long): LyricLine? {
        val idx = currentLineIndex(lines, positionMs)
        return if (idx >= 0 && idx < lines.size) lines[idx] else null
    }
}
