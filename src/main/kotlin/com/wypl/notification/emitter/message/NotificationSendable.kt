package com.wypl.notification.emitter.message

interface NotificationSendable {
    fun getMessageType(): MessageType

    fun getData(): Map<String, Any>
}