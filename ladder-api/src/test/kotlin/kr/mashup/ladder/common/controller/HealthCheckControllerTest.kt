package kr.mashup.ladder.common.controller

import io.kotest.core.spec.style.FunSpec
import kr.mashup.ladder.common.dto.response.ApiResponse
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
@SpringBootTest
internal class HealthCheckControllerTest(
    private val mockMvc: MockMvc,
) : FunSpec({

    test("Health Check 200 OK") {
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

})
