package com.viorayls.app.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.viorayls.app.model.PlaybackState
import com.viorayls.app.model.RepeatMode
import com.viorayls.app.model.Track

class MusicPlayer(private val context: Context) {

    private val player = ExoPlayer.Builder(context).build()

    private val _state = MutableStateFlow(PlaybackState(track = null, isPlaying = false))
    val state: StateFlow<PlaybackState> = _state.asStateFlow()

    private var currentTrack: Track? = null

    init {
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                val current = _state.value
                _state.value = current.copy(
                    isPlaying = playbackState == Player.STATE_READY && player.isPlaying
                )
            }
            override fun onPositionDiscontinuity(
                oldPosition: Player.PositionInfo,
                newPosition: Player.PositionInfo,
                reason: Int
            ) {
                val current = _state.value
                _state.value = current.copy(positionMs = player.currentPosition)
            }
            override fun onPlayerError(error: PlaybackException) {
                // Error handled silently; UI shows last known state
            }
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                val current = _state.value
                _state.value = current.copy(isPlaying = isPlaying)
            }
        })
    }

    fun load(track: Track) {
        currentTrack = track
        val mediaItem = MediaItem.fromUri(track.previewUrl ?: "")
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
        _state.value = PlaybackState(
            track = track,
            isPlaying = true,
            positionMs = 0,
            bufferedMs = 0,
            durationMs = player.duration,
            repeatMode = _state.value.repeatMode,
            shuffle = _state.value.shuffle
        )
    }

    fun play() { player.play() }
    fun pause() { player.pause() }
    fun seekTo(positionMs: Long) { player.seekTo(positionMs) }

    fun next() { player.seekToNext() }
    fun previous() { player.seekToPrevious() }

    fun setRepeat(mode: RepeatMode) {
        val repeat = when (mode) {
            RepeatMode.OFF -> Player.REPEAT_MODE_OFF
            RepeatMode.ONE -> Player.REPEAT_MODE_ONE
            RepeatMode.ALL -> Player.REPEAT_MODE_ALL
        }
        player.setRepeatMode(repeat)
        _state.value = _state.value.copy(repeatMode = mode)
    }

    fun setShuffle(shuffle: Boolean) {
        player.setShuffleModeEnabled(shuffle)
        _state.value = _state.value.copy(shuffle = shuffle)
    }

    val durationMs: Long
        get() = player.duration
    val positionMs: Long
        get() = player.currentPosition

    fun release() {
        player.release()
    }
}
