package com.viorayls.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.viorayls.app.R
import com.viorayls.app.ui.theme.AppTheme
import com.viorayls.app.viewmodel.PlayerViewModel
import coil.compose.rememberImagePainter

@Composable
fun MiniPlayer(playerViewModel: PlayerViewModel, onOpenPlayer: () -> Unit) {
    val state by playerViewModel.playbackState.collectAsState()
    val track = state.track ?: return

    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth().height(68.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(track.artworkUrl ?: ""),
                contentDescription = null,
                modifier = Modifier.size(44.dp).clip(CircleShape).clickable(onOpenPlayer)
            )
            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(text = track.title, style = MaterialTheme.typography.titleMedium, maxLines = 1)
                Text(text = track.artist, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
            }
            IconButton(onClick = { playerViewModel.playPause() }) {
                Icon(
                    imageVector = androidx.compose.ui.graphics.vector.ImageVector.vectorResource(
                        id = if (state.isPlaying) R.drawable.ic_pause else R.drawable.ic_play
                    ),
                    contentDescription = if (state.isPlaying) "Pause" else "Play"
                )
            }
            IconButton(onClick = onOpenPlayer) {
                Icon(
                    imageVector = androidx.compose.ui.graphics.vector.ImageVector.vectorResource(id = R.drawable.ic_more),
                    contentDescription = "More"
                )
            }
        }
    }
}
