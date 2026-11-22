package com.viorayls.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.viorayls.app.ui.components.TrackRow
import com.viorayls.app.ui.theme.AppTheme
import com.viorayls.app.viewmodel.MusicViewModel
import com.viorayls.app.viewmodel.PlayerViewModel
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    navController: NavHostController,
    musicViewModel: MusicViewModel,
    playerViewModel: PlayerViewModel
) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(text = "Home", style = MaterialTheme.typography.displayLarge)
        Text(text = "Browse your music", style = MaterialTheme.typography.bodyMedium)
        androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(24.dp))

        sectionHeader("Trending", navController)
        sectionHeader("Albums", navController)
        sectionHeader("Artists", navController)
        sectionHeader("Playlists", navController)
        sectionHeader("Favorites", navController)
        sectionHeader("Recently Played", navController)
    }
}

@Composable
private fun sectionHeader(title: String, navController: NavHostController) {
    androidx.compose.foundation.layout.Row(
        modifier = androidx.compose.foundation.layout.fillMaxWidth(),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        androidx.compose.foundation.layout.Spacer(modifier = androidx.compose.foundation.layout.weight(1f))
        androidx.compose.material3.TextButton(
            onClick = { navController.navigate("search") }
        ) {
            Text(text = "View all")
        }
    }
    androidx.compose.foundation.layout.Spacer(modifier = androidx.compose.foundation.layout.height(16.dp))
}
