package com.viorayls.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.viorayls.app.R
import com.viorayls.app.ui.theme.AppTheme
import com.viorayls.app.viewmodel.PlayerViewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
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
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_back), contentDescription = "Back")
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
                    modifier = Modifier.size(260.dp).clip(RoundedCornerShape(24.dp)).align(alignment = Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = t.title, style = MaterialTheme.typography.displayLarge, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Text(text = t.artist, style = MaterialTheme.typography.titleLarge, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Text(text = t.album ?: "", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(24.dp))

                val position = state.positionMs
                val duration = state.durationMs.coerceAtLeast(0L)
                Text(text = formatMs(position), modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Slider(
                    value = position.coerceIn(0, duration).toFloat(),
                    onValueChange = { playerViewModel.seek(it.toLong()) },
                    valueRange = 0f..duration.toFloat(),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                )
                Text(text = formatMs(duration), modifier = Modifier.align(alignment = Alignment.CenterHorizontally))

                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { playerViewModel.previous() }) {
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_previous), contentDescription = "Previous")
                    }
                    IconButton(onClick = { playerViewModel.playPause() }) {
                        Icon(imageVector = ImageVector.vectorResource(id = if (state.isPlaying) R.drawable.ic_pause else R.drawable.ic_play), contentDescription = if (state.isPlaying) "Pause" else "Play")
                    }
                    IconButton(onClick = { playerViewModel.next() }) {
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_next), contentDescription = "Next")
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { playerViewModel.setShuffle(!state.shuffle) }) {
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_shuffle), contentDescription = "Shuffle")
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                    IconButton(onClick = { playerViewModel.setRepeat() }) {
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_repeat), contentDescription = "Repeat")
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                    IconButton(onClick = { /* queue */ }) {
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_queue), contentDescription = "Queue")
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                    IconButton(onClick = { navController.navigate("lyrics/${t.id}") }) {
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_lyrics), contentDescription = "Lyrics")
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
