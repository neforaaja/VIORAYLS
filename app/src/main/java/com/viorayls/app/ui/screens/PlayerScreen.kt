package com.viorayls.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.viorayls.app.R
import com.viorayls.app.ui.theme.AppTheme
import com.viorayls.app.viewmodel.PlayerViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import androidx.compose.foundation.Image

@Composable
fun PlayerScreen(
    navController: NavHostController,
    trackId: Long,
    playerViewModel: PlayerViewModel
) {
    val state by playerViewModel.playbackState.collectAsState()
    val track = state.track

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Now Playing") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = androidx.compose.ui.graphics.vector.ImageVector.vectorResource(id = R.drawable.ic_back), contentDescription = "Back")
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            track?.let { t ->
                Image(
                    painter = rememberImagePainter(t.artworkUrl ?: ""),
                    contentDescription = null,
                    modifier = Modifier.size(260.dp).clip(androidx.compose.foundation.shape.CornerSize(24.dp)).align(alignment = Alignment.CenterHorizontally)
                )
                androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(24.dp))
                Text(text = t.title, style = MaterialTheme.typography.displayLarge, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Text(text = t.artist, style = MaterialTheme.typography.titleLarge, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Text(text = t.album ?: "", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(24.dp))

                val position = state.positionMs
                val duration = state.durationMs.coerceAtLeast(1)
                Text(text = formatMs(position), modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Slider(
                    value = position.coerceIn(0, duration).toFloat(),
                    onValueChange = { playerViewModel.seek(it.toLong()) },
                    valueRange = 0f..duration.toFloat(),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                )
                Text(text = formatMs(duration), modifier = Modifier.align(alignment = Alignment.CenterHorizontally))

                androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(32.dp))
                androidx.compose.foundation.layout.Row(
                    horizontalArrangement = androidx.compose.foundation.layout.HorizontalArrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { playerViewModel.previous() }) {
                        Icon(imageVector = androidx.compose.ui.graphics.vector.ImageVector.vectorResource(id = R.drawable.ic_previous), contentDescription = "Previous")
                    }
                    IconButton(onClick = { playerViewModel.playPause() }) {
                        Icon(imageVector = androidx.compose.ui.graphics.vector.ImageVector.vectorResource(id = if (state.isPlaying) R.drawable.ic_pause else R.drawable.ic_play), contentDescription = if (state.isPlaying) "Pause" else "Play")
                    }
                    IconButton(onClick = { playerViewModel.next() }) {
                        Icon(imageVector = androidx.compose.ui.graphics.vector.ImageVector.vectorResource(id = R.drawable.ic_next), contentDescription = "Next")
                    }
                }
                androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(20.dp))
                androidx.compose.foundation.layout.Row(
                    horizontalArrangement = androidx.compose.foundation.layout.HorizontalArrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { playerViewModel.setShuffle(!state.shuffle) }) {
                        Icon(imageVector = androidx.compose.ui.graphics.vector.ImageVector.vectorResource(id = R.drawable.ic_shuffle), contentDescription = "Shuffle")
                    }
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(24.dp))
                    IconButton(onClick = { playerViewModel.setRepeat() }) {
                        Icon(imageVector = androidx.compose.ui.graphics.vector.ImageVector.vectorResource(id = R.drawable.ic_repeat), contentDescription = "Repeat")
                    }
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(24.dp))
                    IconButton(onClick = { /* queue */ }) {
                        Icon(imageVector = androidx.compose.ui.graphics.vector.ImageVector.vectorResource(id = R.drawable.ic_queue), contentDescription = "Queue")
                    }
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(24.dp))
                    IconButton(onClick = { navController.navigate("lyrics/${t.id}") }) {
                        Icon(imageVector = androidx.compose.ui.graphics.vector.ImageVector.vectorResource(id = R.drawable.ic_lyrics), contentDescription = "Lyrics")
                    }
                }
            }
        }
    }
}

private fun formatMs(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}
