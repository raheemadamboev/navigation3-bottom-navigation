package xyz.teamgravity.navigation3bottomnavigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.ui.NavDisplay

@Composable
fun Navigation(
    state: NavigationState = rememberNavigationState(
        startRoute = Route.TodoList,
        topLevelRoutes = TOP_LEVEL_ROUTES.keys
    ),
    navigator: Navigator = remember {
        Navigator(state)
    }
) {
    Scaffold(
        bottomBar = {
            BottomAppBar {
                TOP_LEVEL_ROUTES.entries.forEach { (topLevelRoute, data) ->
                    NavigationBarItem(
                        selected = topLevelRoute == state.topLevelRoute,
                        onClick = {
                            navigator.navigate(topLevelRoute)
                        },
                        icon = {
                            Icon(
                                imageVector = data.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = data.title
                            )
                        }
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        NavDisplay(
            onBack = navigator::goBack,
            entries = state.toEntries {
                entry<Route.TodoList> {
                    TodoListScreen(
                        onNavigateTodo = { todo ->
                            navigator.navigate(Route.Todo(todo))
                        }
                    )
                }
                entry<Route.TodoFavorites> {
                    TodoListScreen(
                        onNavigateTodo = { todo ->
                            navigator.navigate(Route.Todo(todo))
                        }
                    )
                }
                entry<Route.Settings> {
                    SettingsScreen()
                }
                entry<Route.Todo> { route ->
                    TodoScreen(
                        todoExtra = route.todoExtra
                    )
                }
            }
        )
    }
}