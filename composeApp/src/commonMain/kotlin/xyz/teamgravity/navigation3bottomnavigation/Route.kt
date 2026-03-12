package xyz.teamgravity.navigation3bottomnavigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object TodoList : Route

    @Serializable
    data object TodoFavorites : Route

    @Serializable
    data class Todo(val todoExtra: String) : Route

    @Serializable
    data object Settings : Route
}