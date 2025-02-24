package com.touhidapps.room

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform