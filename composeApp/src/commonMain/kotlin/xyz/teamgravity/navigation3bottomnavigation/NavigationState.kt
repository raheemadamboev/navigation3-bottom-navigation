package xyz.teamgravity.navigation3bottomnavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.savedstate.compose.serialization.serializers.MutableStateSerializer
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

class NavigationState(
    val startRoute: NavKey,
    topLevelRoute: MutableState<NavKey>,
    val stacks: Map<NavKey, NavBackStack<NavKey>>
) {
    var topLevelRoute: NavKey by topLevelRoute

    val stacksInUse: List<NavKey>
        get() = buildList {
            add(startRoute)
            if (topLevelRoute != startRoute) add(topLevelRoute)
        }
}

private val CONFIGURATION: SavedStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Route.TodoList::class, Route.TodoList.serializer())
            subclass(Route.TodoFavorites::class, Route.TodoFavorites.serializer())
            subclass(Route.Todo::class, Route.Todo.serializer())
            subclass(Route.Settings::class, Route.Settings.serializer())
        }
    }
}

@Composable
fun rememberNavigationState(
    startRoute: NavKey,
    topLevelRoutes: Set<NavKey>
): NavigationState {
    val topLevelRoute = rememberSerializable(
        startRoute,
        topLevelRoutes,
        configuration = CONFIGURATION,
        serializer = MutableStateSerializer(PolymorphicSerializer(NavKey::class))
    ) {
        mutableStateOf(startRoute)
    }

    val stacks = topLevelRoutes.associateWith { key ->
        rememberNavBackStack(
            configuration = CONFIGURATION,
            key
        )
    }

    return remember(
        key1 = startRoute,
        key2 = topLevelRoutes
    ) {
        NavigationState(
            startRoute = startRoute,
            topLevelRoute = topLevelRoute,
            stacks = stacks
        )
    }
}

@Composable
fun NavigationState.toEntries(
    builder: EntryProviderScope<NavKey>.() -> Unit
): SnapshotStateList<NavEntry<NavKey>> {
    val decoratedEntries = stacks.mapValues { (_, stack) ->
        val decorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
            rememberViewModelStoreNavEntryDecorator()
        )
        rememberDecoratedNavEntries(
            backStack = stack,
            entryDecorators = decorators,
            entryProvider = entryProvider(
                builder = builder
            )
        )
    }

    return stacksInUse
        .flatMap { decoratedEntries[it] ?: emptyList() }
        .toMutableStateList()
}