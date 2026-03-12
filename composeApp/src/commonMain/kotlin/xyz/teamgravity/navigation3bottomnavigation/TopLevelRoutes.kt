package xyz.teamgravity.navigation3bottomnavigation

val TOP_LEVEL_ROUTES: Map<Route, BottomNavigationMenu> = mapOf(
    Route.TodoList to BottomNavigationMenu.TodoList,
    Route.TodoFavorites to BottomNavigationMenu.TodoFavorite,
    Route.Settings to BottomNavigationMenu.Settings
)