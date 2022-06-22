package kr.mashup.ladder.common.advice

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import kr.mashup.ladder.IntegrationTest
import kr.mashup.ladder.common.controller.HealthCheckController
import kr.mashup.ladder.domain.common.error.ErrorCode
import kr.mashup.ladder.domain.common.error.model.UnknownErrorException
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
internal class ControllerExceptionAdviceTest(
    private val mockMvc: MockMvc,

    @MockkBean
    private val healthCheckController: HealthCheckController,
) : IntegrationTest() {

    @Test
    fun `UnKnownException 발생시 500 에러로 반환된다`() {
        // given
        every { healthCheckController.health() } throws UnknownErrorException("알 수 없는 에러가 발생하였습니다")

        // when & then
        mockMvc.get("/api/health")
            .andExpect { status { isInternalServerError() } }
            .andExpect { jsonPath("$.code") { value(ErrorCode.UNKNOWN_ERROR.code) } }
            .andExpect { jsonPath("$.message") { value(ErrorCode.UNKNOWN_ERROR.message) } }
            .andExpect { jsonPath("$.data") { value(null) } }
    }

    @Test
    fun `따로 지정되지 않은 Exception 이 발생하면 500 에러로 반환한다`() {
        // given
        every { healthCheckController.health() } throws IllegalArgumentException("알 수 없는 에러가 발생하였습니다")

        // when & then
        mockMvc.get("/api/health")
            .andExpect { status { isInternalServerError() } }
            .andExpect { jsonPath("$.code") { value(ErrorCode.UNKNOWN_ERROR.code) } }
            .andExpect { jsonPath("$.message") { value(ErrorCode.UNKNOWN_ERROR.message) } }
            .andExpect { jsonPath("$.data") { value(null) } }
    }

}

