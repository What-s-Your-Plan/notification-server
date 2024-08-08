package com.wypl.notification.global.redis.embedded

enum class OS(private val value: String) {
    MAC("Mac"),
    WINDOWS("Windows"),
    LINUX("Linux"),
    UBUNTU("Ubuntu"),
    DEBIAN("Debian");

    fun contains(osName: String): Boolean {
        return osName.contains(value)
    }
}
