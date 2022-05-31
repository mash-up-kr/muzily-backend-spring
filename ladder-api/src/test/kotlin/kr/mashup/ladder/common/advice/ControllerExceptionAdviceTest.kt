package kr.mashup.ladder.common.advice

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import kr.mashup.ladder.common.controller.HealthCheckController
import kr.mashup.ladder.domain.common.error.ErrorCode
import kr.mashup.ladder.domain.common.error.model.ForbiddenException
import kr.mashup.ladder.domain.common.error.model.NotFoundException
import kr.mashup.ladder.domain.common.error.model.UnknownErrorException
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
@SpringBootTest
internal class ControllerExceptionAdviceTest(
    private val mockMvc: MockMvc,

    @MockkBean
    private val healthCheckController: HealthCheckController,
) : FunSpec({

    context("handling LadderBaseException") {
        test("404 NotFound 예외 발생시 404 status code로 반환된다") {
            // given
            every { healthCheckController.health() } throws NotFoundException("해당하는 리소스는 존재하지 않습니다")

            // when & then
            mockMvc.get("/health")
                .andExpect { status { isNotFound() } }
                .andExpect { jsonPath("$.code") { value(ErrorCode.NOT_FOUND.code) } }
                .andExpect { jsonPath("$.message") { value(ErrorCode.NOT_FOUND.message) } }
                .andExpect { jsonPath("$.data") { value(null) } }
        }

        test("403 Forbidden 예외 발생시 403 status code로 반환된다") {
            // given
            every { healthCheckController.health() } throws ForbiddenException("해당하는 권한이 존재하지 않습니다")

            // when & then
            mockMvc.get("/health")
                .andExpect { status { isForbidden() } }
                .andExpect { jsonPath("$.code") { value(ErrorCode.FORBIDDEN.code) } }
                .andExpect { jsonPath("$.message") { value(ErrorCode.FORBIDDEN.message) } }
                .andExpect { jsonPath("$.data") { value(null) } }
        }

        test("UnKnownException 발생시 500 에러로 반환된다") {
            // given
            every { healthCheckController.health() } throws UnknownErrorException("알 수 없는 에러가 발생하였습니다")

            // when & then
            mockMvc.get("/health")
                .andExpect { status { isInternalServerError() } }
                .andExpect { jsonPath("$.code") { value(ErrorCode.UNKNOWN_ERROR.code) } }
                .andExpect { jsonPath("$.message") { value(ErrorCode.UNKNOWN_ERROR.message) } }
                .andExpect { jsonPath("$.data") { value(null) } }
        }
    }

    test("따로 지정되지 않은 Exception 이 발생하면 500 에러로 반환한다") {
        // given
        every { healthCheckController.health() } throws IllegalArgumentException("알 수 없는 에러가 발생하였습니다")

        // when & then
        mockMvc.get("/health")
            .andExpect { status { isInternalServerError() } }
            .andExpect { jsonPath("$.code") { value(ErrorCode.UNKNOWN_ERROR.code) } }
            .andExpect { jsonPath("$.message") { value(ErrorCode.UNKNOWN_ERROR.message) } }
            .andExpect { jsonPath("$.data") { value(null) } }
    }

})
