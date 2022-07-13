package kr.mashup.ladder.auth

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import kr.mashup.ladder.AcceptanceTest
import kr.mashup.ladder.auth.AuthFixture.Companion.`인증 요청값`
import kr.mashup.ladder.auth.dto.request.AuthRequest
import kr.mashup.ladder.auth.dto.response.LoginResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class AuthAcceptanceTest : AcceptanceTest() {
    @Test
    fun `SNS 계정으로 인증한다`() {
        // given

        // when
        val response = `SNS 계정으로 인증 요청`(`인증 요청값`())

        // then
        `SNS 계정으로 인증됨`(response)
    }

    @Test
    fun `익명으로 회원가입한다`() {
        // given

        // when
        val response = `익명 회원가입 요청`()

        // then
        `익명 회원가입됨`(response)
    }

    companion object {
        fun `SNS 계정으로 인증 요청`(request: AuthRequest): ExtractableResponse<Response> {
            return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .`when`().post("/test-api/v1/auth")
                .then().log().all()
                .extract()
        }

        fun `SNS 계정으로 인증됨`(response: ExtractableResponse<Response>) {
            assertAll(
                { assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()) },
                { assertThat(response.`as`(LoginResponse::class.java).token).isNotNull() }
            )
        }

        fun `SNS 계정으로 인증되어 있음`(request: AuthRequest): LoginResponse {
            return `SNS 계정으로 인증 요청`(request).`as`(LoginResponse::class.java)
        }

        fun `익명 회원가입 요청`(): ExtractableResponse<Response> {
            return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .`when`().post("/test-api/v1/auth/anonymous")
                .then().log().all()
                .extract()
        }

        fun `익명 회원가입됨`(response: ExtractableResponse<Response>) {
            assertAll(
                { assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()) },
                { assertThat(response.`as`(LoginResponse::class.java).token).isNotNull() }
            )
        }

        fun `익명 회원가입되어 있음`(): LoginResponse {
            return `익명 회원가입 요청`().`as`(LoginResponse::class.java)
        }
    }
}
