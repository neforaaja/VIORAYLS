package com.viorayls.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.viorayls.app.viewmodel.PlayerViewModel

@Composable
fun LibraryScreen(
    navController: NavHostController,
    playerViewModel: PlayerViewModel
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Library", style = MaterialTheme.typography.displayLarge)
        Text(text = "Your music collection", style = MaterialTheme.typography.bodyMedium)
    }
}
