package com.wypl.notification.global.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

interface Subscribable {
    fun subscribe(memberId: Int): ResponseEntity<SseEmitter>
}