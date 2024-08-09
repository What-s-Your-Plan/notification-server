package com.wypl.notification.emitter.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.wypl.notification.common.ControllerTest
import com.wypl.notification.emitter.service.EmitterSubscribeService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

class NotificationControllerTest : ControllerTest() {
    @MockBean
    private lateinit var emitterSubscribeService: EmitterSubscribeService

    @DisplayName("SSE 연결, POST - /notification/v1/subscribe")
    @Test
    fun uploadImage() {
        /* Given */
        val sseEmitter: SseEmitter = mock()
        given(this.emitterSubscribeService.connections(anyInt())).willReturn(sseEmitter)

        val request = SubscribeRequest(
            accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
        );

        /* When */
        val actions = mockMvc!!.perform(
            RestDocumentationRequestBuilders.post("/notification/v1/subscribe/{memberId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertToJson(request))
        )

        /* Then */
        actions.andDo(MockMvcResultHandlers.print())
            .andDo(
                document(
                    "notification/v1/subscribe",
                    Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    RequestDocumentation.pathParameters(
                        RequestDocumentation.parameterWithName("memberId")
                            .description("요청한 사용자 식별자")
                    ),
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("access_token")
                            .type(JsonFieldType.STRING)
                            .description("사용자 ACCESS_TOKEN"),
                    )
                )
            ).andExpect(MockMvcResultMatchers.status().isCreated())
    }
}