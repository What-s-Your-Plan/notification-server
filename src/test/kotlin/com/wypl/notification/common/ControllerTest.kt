package com.wypl.notification.common

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.wypl.notification.emitter.controller.NotificationController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@WebMvcTest(
    NotificationController::class
)
abstract class ControllerTest {
    @Autowired
    protected var mockMvc: MockMvc? = null

    @Autowired
    protected var objectMapper: ObjectMapper? = null

    protected fun convertToJson(`object`: Any?): String {
        try {
            return objectMapper!!.writeValueAsString(`object`)
        } catch (e: JsonProcessingException) {
            throw AssertionError("올바르지 않는 JSON 형식입니다.")
        }
    }
}