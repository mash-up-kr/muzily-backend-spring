package kr.mashup.ladder.common.controller

import kr.mashup.ladder.IntegrationTest
import kr.mashup.ladder.common.dto.response.ApiResponse
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
internal class HealthCheckControllerTest(
    private val mockMvc: MockMvc,
) : IntegrationTest() {

    @Test
    fun `Health Check 200 OK`() {
        // when & then
        mockMvc.get("/health")
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andExpect {
                jsonPath("$.code") { isEmpty() }
                jsonPath("$.message") { isEmpty() }
                jsonPath("$.data") { value(ApiResponse.OK.data) }
            }
    }

}
