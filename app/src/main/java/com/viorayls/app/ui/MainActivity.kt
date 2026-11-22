package com.viorayls.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.viorayls.app.R
import com.viorayls.app.ui.theme.AppTheme
import com.viorayls.app.viewmodel.MusicViewModel
import com.viorayls.app.viewmodel.PlayerViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        val musicViewModel: MusicViewModel by viewModels { AndroidViewModelFactory(application) }
        val playerViewModel: PlayerViewModel by viewModels()
        setContent {
            AppTheme(darkTheme = true) {
                VioraylsNav(musicViewModel, playerViewModel)
            }
        }
    }
}

@Composable
fun VioraylsNav(musicViewModel: MusicViewModel, playerViewModel: PlayerViewModel) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != "player" && currentRoute != "lyrics") {
                BottomNav(navController)
            }
        },
        content = { padding ->
            NavHost(navController, startDestination = "home", Modifier.fillMaxSize().padding(padding)) {
                composable("home") { HomeScreen(navController, musicViewModel, playerViewModel) }
                composable("search") { SearchScreen(navController, musicViewModel, playerViewModel) }
                composable("explore") { ExploreScreen(navController, playerViewModel) }
                composable("library") { LibraryScreen(navController, playerViewModel) }
                composable("player/{trackId}") {
                    val trackId = it.arguments?.getLong("trackId") ?: 0L
                    PlayerScreen(navController, trackId, playerViewModel)
                }
                composable("lyrics/{trackId}") {
                    val trackId = it.arguments?.getLong("trackId") ?: 0L
                    FullScreenLyricsScreen(navController, trackId, playerViewModel, musicViewModel)
                }
            }
        }
    )
}

@Composable
fun BottomNav(navController: NavHostController) {
    val items = listOf(
        "home" to R.drawable.ic_home,
        "search" to R.drawable.ic_search,
        "explore" to R.drawable.ic_explore,
        "library" to R.drawable.ic_library
    )
    val labels = mapOf("home" to "Home", "search" to "Search", "explore" to "Explore", "library" to "Library")
    var selected by remember { mutableStateOf("home") }

    NavigationBar(
        containerColor = Color(0xFF121212)
    ) {
        items.forEach { (route, iconRes) ->
            val isSelected = selected == route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    selected = route
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = iconRes),
                        contentDescription = labels[route]
                    )
                },
                label = {
                    Text(
                        text = labels[route] ?: "",
                        color = androidx.compose.material3.LocalContentColor.current.copy(alpha = if (isSelected) 1f else 0.5f)
                    )
                }
            )
        }
    }
}
