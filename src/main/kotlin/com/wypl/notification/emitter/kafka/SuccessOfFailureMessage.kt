package com.wypl.notification.emitter.kafka

import com.fasterxml.jackson.annotation.JsonProperty
import com.wypl.notification.global.kafka.KafkaProducerMessage

data class SuccessOfFailureMessage(
    @JsonProperty("recipient_id") val recipientId: Long,
    @JsonProperty("message") val message: String
) : KafkaProducerMessage()
