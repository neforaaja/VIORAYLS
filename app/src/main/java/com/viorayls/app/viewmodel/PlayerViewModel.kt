package com.viorayls.app.viewmodel

import androidx.lifecycle.ViewModel
import com.viorayls.app.model.PlaybackState
import com.viorayls.app.model.Track
import com.viorayls.app.player.PlayerManager
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.viewModelScope

class PlayerViewModel : ViewModel() {
    val playbackState: StateFlow<PlaybackState> = PlayerManager.stateFlow().stateIn(viewModelScope)

    fun play(track: Track) {
        PlayerManager.instance.load(track)
    }

    fun playPause() {
        val p = PlayerManager.instance
        if (p.state.value.isPlaying) p.pause() else p.play()
    }

    fun next() = PlayerManager.instance.next()
    fun previous() = PlayerManager.instance.previous()
    fun seek(positionMs: Long) = PlayerManager.instance.seekTo(positionMs)
    fun setRepeat() {
        val m = PlayerManager.instance.state.value.repeatMode
        val next = when (m) {
            com.viorayls.app.model.RepeatMode.OFF -> com.viorayls.app.model.RepeatMode.ONE
            com.viorayls.app.model.RepeatMode.ONE -> com.viorayls.app.model.RepeatMode.ALL
            com.viorayls.app.model.RepeatMode.ALL -> com.viorayls.app.model.RepeatMode.OFF
        }
        PlayerManager.instance.setRepeat(next)
    }
    fun setShuffle(shuffle: Boolean) = PlayerManager.instance.setShuffle(shuffle)
}
