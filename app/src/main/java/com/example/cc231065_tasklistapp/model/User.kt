package com.example.cc231065_tasklistapp.model

data class User(
    val id: Int,
    val username: String,
    var xp: Int = 0, // Total XP
    var level: Int = 1 // User's level, increment based on XP
)

fun updateUserLevel(user: User) {
    user.level = (user.xp / 1000) + 1 // Level up after every 100 XP
}
