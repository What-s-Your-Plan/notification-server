package com.wypl.notification.global.message

import com.wypl.notification.global.NotificationSendable

data class SubscribeMessage(
    private val memberId: Int,
    private val timeout: Long,
    private val type: MessageType = MessageType.SUBSCRIBE
) : NotificationSendable {
}
