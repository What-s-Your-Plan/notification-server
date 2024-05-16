package com.wypl.notification.emitter

import com.wypl.notification.global.controller.Subscribable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RequestMapping("/notification")
@RestController
class NotificationController(
    private val notificationService: NotificationService
) : Subscribable {

    @GetMapping("/v1/subscribe", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    override fun subscribe(@RequestParam memberId: Int): ResponseEntity<SseEmitter> {
        val sseEmitter: SseEmitter = notificationService.subscribe(memberId)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(sseEmitter)
    }
}