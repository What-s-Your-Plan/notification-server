package com.wypl.notification.emitter

import com.wypl.notification.global.NotificationSendable
import com.wypl.notification.global.message.SubscribeMessage
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException

private val logger = KotlinLogging.logger {}

@Service
class NotificationService(
    private val sseEmitterRepository: SseEmitterRepository
) {
    companion object {
        const val TIME_OUT_MILLISECONDS: Long = 60 * 1_000L
    }

    fun subscribe(memberId: Int): SseEmitter {
        val sseEmitter = SseEmitter(TIME_OUT_MILLISECONDS);
        sseEmitterRepository.save(memberId, sseEmitter)

        sseEmitterCallback(sseEmitter, memberId)

        return sendMessage(
            message = SubscribeMessage(
                memberId = memberId,
                timeout = TIME_OUT_MILLISECONDS
            ),
            memberId,
            sseEmitter
        )
    }

    private fun sseEmitterCallback(
        sseEmitter: SseEmitter,
        memberId: Int
    ) {
        sseEmitter.onCompletion {
            logger.trace { "\n\n${memberId}번 사용자의 연결이 만료되어 삭제합니다.\n" }
            sseEmitterRepository.deleteById(memberId)
        }
        sseEmitter.onTimeout {
            logger.trace { "\n\n${memberId}번 사용자의 연결 요청이 Timeout으로 인해 종료합니다.\n" }
            sseEmitter.complete()
        }
        sseEmitter.onError {
            logger.trace { "\n\n${memberId}번 사용자의 연결 요청이 실패하여 종료합니다.\n" }
            sseEmitter.complete()
        }
    }

    fun sendMessage(message: NotificationSendable, memberId: Int, sseEmitter: SseEmitter): SseEmitter {
        try {
            sseEmitter.send(
                SseEmitter.event()
                    .id(memberId.toString())
                    .data(message, MediaType.APPLICATION_JSON)
            )
        } catch (e: IOException) {
            sseEmitterRepository.deleteById(memberId)
        }
        return sseEmitter
    }
}