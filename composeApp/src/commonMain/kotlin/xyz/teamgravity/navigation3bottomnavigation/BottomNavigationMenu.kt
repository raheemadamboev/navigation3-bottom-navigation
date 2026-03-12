package xyz.teamgravity.navigation3bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavigationMenu(
    val icon: ImageVector,
    val title: String
) {
    TodoList(
        icon = Icons.Rounded.Event,
        title = "Todos"
    ),
    TodoFavorite(
        icon = Icons.Rounded.Favorite,
        title = "Favorites"
    ),
    Settings(
        icon = Icons.Rounded.Settings,
        title = "Settings"
    );
}