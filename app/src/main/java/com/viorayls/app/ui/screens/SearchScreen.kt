package com.viorayls.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.viorayls.app.ui.components.TrackRow
import com.viorayls.app.ui.theme.AppTheme
import com.viorayls.app.viewmodel.MusicViewModel
import com.viorayls.app.viewmodel.PlayerViewModel
import androidx.navigation.NavHostController

@Composable
fun SearchScreen(
    navController: NavHostController,
    musicViewModel: MusicViewModel,
    playerViewModel: PlayerViewModel
) {
    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Search", style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text(text = "Search songs, artists, albums") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        androidx.compose.material3.Button(
            onClick = { musicViewModel.search(query) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Search")
        }
        Spacer(modifier = Modifier.height(20.dp))

        if (musicViewModel.loading.value) {
            Text(text = "Searching...")
        } else {
            val results = musicViewModel.tracks.value
            if (results.isEmpty()) {
                Text(text = "No results.", style = MaterialTheme.typography.bodyMedium)
            } else {
                Text(text = "Results", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(12.dp))
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(results) { track ->
                        TrackRow(
                            track = track,
                            onPlay = { playerViewModel.play(track); navController.navigate("player/${track.id}") },
                            onClick = { playerViewModel.play(track); navController.navigate("player/${track.id}") }
                        )
                    }
                }
            }
        }
    }
}
