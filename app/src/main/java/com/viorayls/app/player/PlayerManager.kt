package com.viorayls.app.player

import android.content.Context
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import com.viorayls.app.model.PlaybackState

object PlayerManager {
    private var player: MusicPlayer? = null

    fun init(context: Context) {
        if (player == null) {
            player = MusicPlayer(context.applicationContext)
        }
    }

    val instance: MusicPlayer
        get() = player ?: error("PlayerManager not initialized. Call init(context) first.")

    fun stateFlow(): StateFlow<PlaybackState> = instance.state

    fun release() {
        player?.release()
        player = null
    }
}
