package com.wypl.notification.global.redis.embedded

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.util.StringUtils
import java.io.IOException

private val logger = KotlinLogging.logger {}

class RedisAvailablePortFindableForMacOS : RedisAvailablePortFindable() {

    override fun findAvailablePort(redisPort: Int): Int {
        for (port in 10000..65535) {
            val process = executeGrepProcessCommand(port)
            if (!isRunning(process)) {
                return port
            }
        }
        throw IllegalArgumentException("Not Found Available port: 10000 ~ 65535")
    }

    override fun isRedisRunning(redisPort: Int): Boolean {
        return isRunning(executeGrepProcessCommand(redisPort))
    }

    private fun isRunning(process: Process): Boolean {
        val pidInfo = StringBuilder()
        try {
            process.inputStream.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    pidInfo.append(line)
                }
            }
        } catch (e: Exception) {
            logger.error(e) { "\n\n${e.message}\n" }
        }
        return StringUtils.hasText(pidInfo.toString())
    }

    @Throws(IOException::class)
    private fun executeGrepProcessCommand(port: Int): Process {
        val command = "netstat -nat | grep LISTEN | grep $port"
        val shell = arrayOf("/bin/sh", "-c", command)
        return Runtime.getRuntime().exec(shell)
    }
}
