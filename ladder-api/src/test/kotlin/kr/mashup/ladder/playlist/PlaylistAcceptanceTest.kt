package kr.mashup.ladder.playlist

import io.restassured.RestAssured
import io.restassured.http.Header
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import kr.mashup.ladder.AcceptanceTest
import kr.mashup.ladder.auth.AuthAcceptanceTest.Companion.`SNS 계정 로그인되어 있음`
import kr.mashup.ladder.auth.AuthAcceptanceTest.Companion.`익명 로그인되어 있음`
import kr.mashup.ladder.auth.AuthFixture.Companion.`인증 요청값`
import kr.mashup.ladder.playlist.dto.PlaylistDto
import kr.mashup.ladder.playlist.dto.PlaylistItemDto
import kr.mashup.ladder.room.RoomAcceptanceTest.Companion.`방 구독되어 있음`
import kr.mashup.ladder.room.RoomAcceptanceTest.Companion.`방 생성되어 있음`
import kr.mashup.ladder.room.RoomAcceptanceTest.Companion.`방 입장되어 있음`
import kr.mashup.ladder.room.RoomAcceptanceTest.Companion.`웹소켓 연결되어 있음`
import kr.mashup.ladder.room.RoomAcceptanceTest.Companion.`재생목록 항목 신청되어 있음`
import kr.mashup.ladder.room.RoomFixture.Companion.`방 생성 요청값`
import kr.mashup.ladder.room.RoomFixture.Companion.`방 재생목록 항목 신청 요청값`
import kr.mashup.ladder.room.dto.request.RoomSendPlaylistItemRequestRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.http.HttpStatus

class PlaylistAcceptanceTest : AcceptanceTest() {
    @Test
    fun `재생목록을 조회한다`() {
        // given
        val `SNS 계정 로그인 응답` = `SNS 계정 로그인되어 있음`(`인증 요청값`())
        val 방 = `방 생성되어 있음`(`SNS 계정 로그인 응답`.token, `방 생성 요청값`())

        // when
        val response = `재생목록 조회 요청`(`SNS 계정 로그인 응답`.token, 방.playlist?.playlistId!!)

        // then
        `재생목록 조회됨`(response, 방.playlist?.playlistId!!)
    }

    @Test
    fun `로그인하지 않은 경우 재생목록을 조회할 수 없다`() {
        // given
        val `SNS 계정 로그인 응답` = `SNS 계정 로그인되어 있음`(`인증 요청값`())
        val 방 = `방 생성되어 있음`(`SNS 계정 로그인 응답`.token, `방 생성 요청값`())

        // when
        val response = `재생목록 조회 요청`("unknown-token", 방.playlist?.playlistId!!)

        // then
        `재생목록 조회되지 않음`(response)
    }

    @Test
    fun `SNS 계정 회원이 계류중인 재생목록 항목들을 조회한다`() {
        // given
        val `SNS 계정 로그인 응답` = `SNS 계정 로그인되어 있음`(`인증 요청값`())
        val `익명 로그인 응답` = `익명 로그인되어 있음`()
        val 방 = `방 생성되어 있음`(`SNS 계정 로그인 응답`.token, `방 생성 요청값`())
        `방 입장되어 있음`(`익명 로그인 응답`.token, 방.invitation.invitationKey)
        val `SNS 계정 세션` = `웹소켓 연결되어 있음`(port, `SNS 계정 로그인 응답`.token)
        val `익명 세션` = `웹소켓 연결되어 있음`(port, `익명 로그인 응답`.token)
        `방 구독되어 있음`(`SNS 계정 세션`, 방.roomId)
        `방 구독되어 있음`(`익명 세션`, 방.roomId)
        val `방 재생목록 항목 신청 요청값들` = listOf(`방 재생목록 항목 신청 요청값`(방.playlist?.playlistId!!))
        `방 재생목록 항목 신청 요청값들`.forEach { `재생목록 항목 신청되어 있음`(`익명 세션`, `SNS 계정 세션`, 방.roomId, it) }

        // when
        val response = `계류중인 재생목록 항목 목록 조회 요청`(`SNS 계정 로그인 응답`.token, 방.playlist?.playlistId!!)

        // then
        `계류중인 재생목록 항목 목록 조회됨`(response, `방 재생목록 항목 신청 요청값들`)
    }

    @Test
    fun `익명 회원은 계류중인 재생목록 항목들을 조회할 수 없다`() {
        // given
        val `SNS 계정 로그인 응답` = `SNS 계정 로그인되어 있음`(`인증 요청값`())
        val `익명 로그인 응답` = `익명 로그인되어 있음`()
        val 방 = `방 생성되어 있음`(`SNS 계정 로그인 응답`.token, `방 생성 요청값`())

        // when
        val response = `계류중인 재생목록 항목 목록 조회 요청`(`익명 로그인 응답`.token, 방.playlist?.playlistId!!)

        // then
        `계류중인 재생목록 항목 목록 조회되지 않음`(response)
    }

    companion object {
        fun `재생목록 조회 요청`(token: String, playlistId: Long): ExtractableResponse<Response> {
            return RestAssured
                .given().log().all()
                .header(Header("Authorization", "Bearer $token"))
                .`when`().get("/api/v1/playlists/{playlistId}", playlistId)
                .then().log().all()
                .extract()
        }

        fun `재생목록 조회됨`(response: ExtractableResponse<Response>, playlistId: Long) {
            val playlist = response.`as`(PlaylistDto::class.java)

            assertAll(
                { assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()) },
                { assertThat(playlist.id).isEqualTo(playlistId) },
                { assertThat(playlist.order).isNotNull() },
            )
        }

        fun `재생목록 조회되지 않음`(response: ExtractableResponse<Response>) {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value())
        }

        fun `계류중인 재생목록 항목 목록 조회 요청`(token: String, playlistId: Long): ExtractableResponse<Response> {
            return RestAssured
                .given().log().all()
                .header(Header("Authorization", "Bearer $token"))
                .`when`().get("/api/v1/playlists/{playlistId}/pending-items", playlistId)
                .then().log().all()
                .extract()
        }

        fun `계류중인 재생목록 항목 목록 조회됨`(
            response: ExtractableResponse<Response>,
            requests: List<RoomSendPlaylistItemRequestRequest>,
        ) {
            val items = response.jsonPath().getList(".", PlaylistItemDto::class.java)

            assertAll(
                { assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()) },
                { assertThat(items).hasSize(requests.size) },
                { assertThat(items.map { it.playlistId }).containsAll(requests.map { it.playlistId }) }
            )
        }

        fun `계류중인 재생목록 항목 목록 조회되지 않음`(response: ExtractableResponse<Response>) {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value())
        }
    }
}
