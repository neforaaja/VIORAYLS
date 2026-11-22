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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.viorayls.app.R
import com.viorayls.app.model.Track
import coil.compose.rememberImagePainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

@Composable
fun TrackRow(
    track: Track,
    onPlay: () -> Unit,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(track.artworkUrl ?: ""),
            contentDescription = null,
            modifier = Modifier.size(48.dp).clip(CircleShape)
        )
        Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
            Text(text = track.title, style = MaterialTheme.typography.titleMedium, maxLines = 1)
            Text(text = track.artist, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
        }
        androidx.compose.material3.IconButton(onClick = onPlay) {
            androidx.compose.material3.Icon(
                imageVector = ImageVector.vectorResource(id = com.viorayls.app.R.drawable.ic_play),
                contentDescription = "Play"
            )
        }
    }
}
