package com.wypl.notification.global.config.embedded

import com.wypl.notification.global.config.embedded.redis.OS
import com.wypl.notification.global.config.embedded.redis.RedisAvailablePortFindable
import com.wypl.notification.global.config.embedded.redis.RedisAvailablePortFindableForMacOS
import com.wypl.notification.global.config.embedded.redis.RedisAvailablePortFindableForWindows
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer

private val logger = KotlinLogging.logger {}

@Profile("default", "test", "local")
@Configuration
class EmbeddedRedisConfig(
    @Value("\${spring.data.redis.port}") private val redisPort: Int
) {
    val osName: String = System.getProperty("os.name")
    lateinit var redisServer: RedisServer

    @PostConstruct
    private fun start() {
        val redisPortFinder: RedisAvailablePortFindable = getRedisAvailablePortFind()

        val port: Int = if (redisPortFinder.isRedisRunning(redisPort)) {
            redisPortFinder.findAvailablePort(redisPort)
        } else {
            redisPort
        }

        logger.info { "\n\nEmbedded Redis Running Port : $port\n" }

        redisServer = RedisServer(port)
        redisServer.start()
    }

    @PreDestroy
    private fun stop() {
        redisServer.stop()
    }

    private fun getRedisAvailablePortFind(): RedisAvailablePortFindable {
        if (OS.MAC.contains(osName)) {
            return RedisAvailablePortFindableForMacOS()
        } else if (OS.WINDOWS.contains(osName)) {
            return RedisAvailablePortFindableForWindows()
        }
        throw IllegalStateException("Unsupported OS: $osName")
    }
}