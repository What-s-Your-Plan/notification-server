package com.wypl.notification.global.redis.embedded

abstract class RedisAvailablePortFindable {
    abstract fun findAvailablePort(redisPort: Int): Int
    abstract fun isRedisRunning(redisPort: Int): Boolean
}