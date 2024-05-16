package com.wypl.notification.global.message

interface NotificationSendable {
    fun getMessageType(): MessageType

    fun getData(): Map<String, Any>
}