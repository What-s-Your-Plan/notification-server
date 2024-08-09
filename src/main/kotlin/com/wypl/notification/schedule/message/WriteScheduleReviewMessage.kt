package com.wypl.notification.schedule.message

import com.fasterxml.jackson.annotation.JsonProperty
import com.wypl.notification.global.kafka.KafkaConsumerMessage

data class WriteScheduleReviewMessage(
    @JsonProperty("recipient_id") val recipientId: Long,
    @JsonProperty("nickname") val nickname: String,
    @JsonProperty("schedule_id") val scheduleId: Long,
    @JsonProperty("schedule_title") val scheduleTitle: String,
) : KafkaConsumerMessage()