package xyz.teamgravity.navigation3bottomnavigation

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform