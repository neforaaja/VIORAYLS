package com.viorayls.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.viorayls.app.repository.LyricsRepository
import com.viorayls.app.repository.MusicRepository
import com.viorayls.app.model.Track
import com.viorayls.app.model.LyricsResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MusicViewModel(application: Application) : AndroidViewModel(application) {
    private val musicRepository = MusicRepository(application)
    private val lyricsRepository = LyricsRepository()

    val tracks = MutableStateFlow<List<Track>>(emptyList())
    var loading = MutableStateFlow(false)

    private val _lyricsResult = MutableStateFlow(LyricsResult.Loading)
    val lyricsResult: StateFlow<LyricsResult> = _lyricsResult.asStateFlow()

    fun search(term: String) {
        loading.value = true
        viewModelScope.launch {
            val result = musicRepository.search(term)
            tracks.value = result
            loading.value = false
        }
    }

    fun loadLyrics(track: Track) {
        viewModelScope.launch {
            val result = lyricsRepository.fetchLyrics(track.title, track.artist, track.album, track.durationMs)
            _lyricsResult.value = result
        }
    }
}
