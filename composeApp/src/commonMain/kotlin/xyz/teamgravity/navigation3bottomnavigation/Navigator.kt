package xyz.teamgravity.navigation3bottomnavigation

import androidx.navigation3.runtime.NavKey

class Navigator(
    private val state: NavigationState
) {

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun navigate(route: NavKey) {
        if (route in state.stacks.keys) {
            state.topLevelRoute = route
        } else {
            state.stacks[state.topLevelRoute]?.add(route)
        }
    }

    fun goBack() {
        val currentStack = state.stacks[state.topLevelRoute] ?: throw IllegalStateException("Backstack for ${state.topLevelRoute} doesn't exist!")
        val currentRoute = currentStack.last()

        if (currentRoute == state.topLevelRoute) {
            state.topLevelRoute = state.startRoute
        } else {
            currentStack.removeLastOrNull()
        }
    }
}