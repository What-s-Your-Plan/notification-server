package com.wypl.notification.global.config.embedded.redis

abstract class RedisAvailablePortFindable {
    abstract fun findAvailablePort(redisPort: Int): Int
    abstract fun isRedisRunning(redisPort: Int): Boolean
}