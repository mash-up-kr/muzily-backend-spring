package kr.mashup.ladder.room

import com.fasterxml.jackson.core.type.TypeReference
import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import kr.mashup.ladder.IntegrationTest
import kr.mashup.ladder.common.dto.response.WsResponse
import kr.mashup.ladder.common.dto.response.WsResponseType
import kr.mashup.ladder.domain.common.error.ErrorCode
import kr.mashup.ladder.domain.room.dto.RoomDto
import kr.mashup.ladder.room.dto.RoomChatRequest
import kr.mashup.ladder.room.dto.RoomCreateRequest
import kr.mashup.ladder.util.StompTestHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.messaging.simp.stomp.StompFrameHandler
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.web.socket.messaging.WebSocketStompClient
import java.lang.reflect.Type
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit


class RoomIntegrationTest : IntegrationTest() {
    val `스타벅스 판교점 방 생성 요청값` = RoomCreateRequest(description = "스타벅스 판교점. 테마는 신나게 🎶")
    val `생활맥주 강남점 방 생성 요청값` = RoomCreateRequest(description = "생활 맥주 강남점. 재즈 🎧")
    val `존재하지 않는 방 ID` = 0L

    @Test
    fun `방을 생성한다`() {
        // given

        // when
        val response = `방 생성 요청`(`스타벅스 판교점 방 생성 요청값`)

        // then
        `방 생성됨`(`스타벅스 판교점 방 생성 요청값`, response)
    }

    fun `방 생성 요청`(request: RoomCreateRequest): ExtractableResponse<Response> {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .`when`().post("/api/v1/rooms")
            .then().log().all()
            .extract()
    }

    fun `방 생성됨`(request: RoomCreateRequest, response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())

        val created = response.jsonPath().getObject("data", RoomDto::class.java)

        assertAll(
            { assertThat(created.roomId).isNotNull() },
            { assertThat(created.description).isEqualTo(request.description) })
    }

    @Test
    fun `방을 조회한다`() {
        // given
        val 방 = `방 생성되어 있음`(`스타벅스 판교점 방 생성 요청값`)

        // when
        val response = `방 조회 요청`(`방`.roomId)

        // then
        `방 조회됨`(`방`, response)
    }

    fun `방 생성되어 있음`(request: RoomCreateRequest): RoomDto {
        return `방 생성 요청`(request).jsonPath().getObject("data", RoomDto::class.java)
    }

    fun `방 조회 요청`(roomId: Long): ExtractableResponse<Response> {
        return RestAssured
            .given().log().all()
            .`when`().get("/api/v1/rooms/{roomId}", roomId)
            .then().log().all()
            .extract()
    }

    fun `방 조회됨`(given: RoomDto, response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())

        val actual = response.jsonPath().getObject("data", RoomDto::class.java)
        assertThat(actual.roomId).isEqualTo(given.roomId)
    }

    @Test
    fun `존재하지 않는 방을 조회한다`() {
        // given

        // when
        val response = `방 조회 요청`(`존재하지 않는 방 ID`)

        // then
        `방 조회되지 않음`(response)
    }

    fun `방 조회되지 않음`(response: ExtractableResponse<Response>) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value())

        val actual = response.jsonPath().getObject("code", String::class.java)
        assertThat(actual).isEqualTo(ErrorCode.ROOM_NOT_FOUND.code)
    }

    @ParameterizedTest
    @CsvSource(value = ["1,HELLO"])
    fun `방에 채팅을 보내고 받는다`(roomId: Long, chat: String) {
        // given
        val future: CompletableFuture<WsResponse<*>> = CompletableFuture()
        val client = StompTestHelper.newClient()
        val session = `웹소켓 연결됨`(client)
        `방에 접속함`(session, roomId, future)

        // when
        `방에 채팅 보내기 요청`(session, roomId, chat)

        // then
        `채팅 받음`(future)
    }

    fun `웹소켓 연결됨`(client: WebSocketStompClient): StompSession {
        return client
            .connect("ws://localhost:${port}/ws", object : StompSessionHandlerAdapter() {})
            .get(1, TimeUnit.SECONDS)
    }

    fun `방에 접속함`(session: StompSession, roomId: Long, future: CompletableFuture<WsResponse<*>>) {
        val handler = object : StompFrameHandler {
            override fun getPayloadType(headers: StompHeaders): Type {
                return object : TypeReference<WsResponse<*>>() {}.type
            }

            override fun handleFrame(headers: StompHeaders, payload: Any?) {
                future.complete(payload as WsResponse<*>)
            }
        }

        session.subscribe("/sub/rooms/${roomId}", handler)
    }

    fun `방에 채팅 보내기 요청`(session: StompSession, roomId: Long, chat: String) {
        session.send("/pub/rooms/${roomId}/chats", RoomChatRequest(chat))
    }

    fun `채팅 받음`(future: CompletableFuture<WsResponse<*>>) {
        val response = future.get(10, TimeUnit.SECONDS)

        assertThat(response.type).isEqualTo(WsResponseType.CHAT)
    }
}
