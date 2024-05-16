package com.wypl.notification.global.config.embedded.redis

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

private val logger = KotlinLogging.logger {}

class RedisAvailablePortFindableForWindows : RedisAvailablePortFindable() {
    override fun findAvailablePort(redisPort: Int): Int {
        for (port in 10000..65535) {
            if (!isRunning(port)) {
                return port
            }
        }
        throw IllegalArgumentException("Not Found Available port: 10000 ~ 65535")
    }

    override fun isRedisRunning(redisPort: Int): Boolean {
        return isRunning(redisPort)
    }

    private fun isRunning(port: Int): Boolean {
        try {
            val builder = ProcessBuilder("netstat", "-nat")
            val process = builder.start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if (line!!.contains("LISTEN") && line!!.contains(port.toString())) {
                    return true
                }
            }
        } catch (e: IOException) {
            logger.error(e) { "\n\n${e.message}\n" }
        }
        return false
    }
}