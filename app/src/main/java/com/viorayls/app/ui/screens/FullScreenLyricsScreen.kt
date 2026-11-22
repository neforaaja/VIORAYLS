package com.viorayls.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.viorayls.app.R
import com.viorayls.app.lyrics.LyricsSync
import com.viorayls.app.model.LyricLine
import com.viorayls.app.model.LyricsResult
import com.viorayls.app.ui.theme.AppTheme
import com.viorayls.app.viewmodel.MusicViewModel
import com.viorayls.app.viewmodel.PlayerViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FullScreenLyricsScreen(
    navController: NavHostController,
    trackId: Long,
    playerViewModel: PlayerViewModel,
    musicViewModel: MusicViewModel
) {
    val state by playerViewModel.playbackState.collectAsState()
    val track = state.track
    val lyricsResult by musicViewModel.lyricsResult.collectAsState()
    val lyricsState = rememberUpdatedState(lyricsResult)

    LaunchedEffect(track) {
        track?.let { musicViewModel.loadLyrics(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lyrics") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_back), contentDescription = "Back")
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (lyricsState.value) {
                is LyricsResult.Loading -> {
                    Text(text = "Loading lyrics...", modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                }
                is LyricsResult.NotFound -> {
                    Text(text = lyricsState.value.message, style = MaterialTheme.typography.titleLarge)
                }
                is LyricsResult.Error -> {
                    Text(text = "Failed to load lyrics.", style = MaterialTheme.typography.titleLarge)
                }
                is LyricsResult.Success -> {
                    val lines = lyricsState.value.lines
                    val currentIdx = if (lines.isNotEmpty()) {
                        LyricsSync.currentLineIndex(lines, state.positionMs)
                    } else -1
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        track?.let { t ->
                            Text(text = t.title, style = MaterialTheme.typography.displayLarge)
                            Text(text = t.artist, style = MaterialTheme.typography.titleLarge)
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        lines.forEachIndexed { index, line ->
                            val isCurrent = index == currentIdx
                            Text(
                                text = line.text,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = if (isCurrent) 24.sp else 20.sp,
                                    fontWeight = if (isCurrent) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal,
                                    color = if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                ),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                        if (lines.isEmpty()) {
                            Text(text = "No synced lyrics available.", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
