package com.wypl.notification.global.config.embedded.redis

enum class OS(private val value: String) {
    MAC("Mac"),
    WINDOWS("Windows 10"),
    LINUX("Linux"),
    UBUNTU("Ubuntu"),
    DEBIAN("Debian");

    fun contains(osName: String): Boolean {
        return osName.contains(value)
    }
}
