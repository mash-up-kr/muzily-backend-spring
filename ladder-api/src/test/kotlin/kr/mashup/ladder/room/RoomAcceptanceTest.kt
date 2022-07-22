package kr.mashup.ladder.room

import com.fasterxml.jackson.core.type.TypeReference
import io.restassured.RestAssured
import io.restassured.http.Header
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import kr.mashup.ladder.AcceptanceTest
import kr.mashup.ladder.auth.AuthAcceptanceTest.Companion.`SNS 계정 로그인되어 있음`
import kr.mashup.ladder.auth.AuthAcceptanceTest.Companion.`익명 로그인되어 있음`
import kr.mashup.ladder.auth.AuthFixture.Companion.`인증 요청값`
import kr.mashup.ladder.common.dto.response.WsResponse
import kr.mashup.ladder.common.dto.response.WsResponseType
import kr.mashup.ladder.config.ws.WS_APP_DESTINATION_PREFIX
import kr.mashup.ladder.config.ws.WS_DESTINATION_PREFIX_QUEUE
import kr.mashup.ladder.config.ws.WS_DESTINATION_PREFIX_TOPIC
import kr.mashup.ladder.config.ws.WS_ENDPOINT
import kr.mashup.ladder.config.ws.WS_USER_DESTINATION_PREFIX
import kr.mashup.ladder.domain.common.exception.ErrorCode
import kr.mashup.ladder.domain.room.domain.emoji.EmojiType
import kr.mashup.ladder.domain.common.util.JsonUtil
import kr.mashup.ladder.room.RoomFixture.Companion.`방 생성 요청값`
import kr.mashup.ladder.room.RoomFixture.Companion.`방 재생목록 항목 신청 승인 요청값`
import kr.mashup.ladder.room.RoomFixture.Companion.`방 재생목록 항목 신청 요청값`
import kr.mashup.ladder.room.RoomFixture.Companion.`방 재생목록 항목 추가 요청값`
import kr.mashup.ladder.room.dto.request.RoomAcceptPlaylistItemRequestRequest
import kr.mashup.ladder.room.dto.request.RoomAddPlaylistItemRequest
import kr.mashup.ladder.room.dto.request.RoomCreateRequest
import kr.mashup.ladder.room.dto.request.RoomSendEmojiRequest
import kr.mashup.ladder.room.dto.request.RoomSendPlaylistItemRequestRequest
import kr.mashup.ladder.room.dto.response.RoomDetailInfoResponse
import kr.mashup.ladder.room.dto.response.RoomEmojiResponse
import kr.mashup.ladder.room.dto.response.RoomPlaylistItemAddResponse
import kr.mashup.ladder.room.dto.response.RoomPlaylistItemRequestResponse
import kr.mashup.ladder.util.StompTestHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.messaging.simp.stomp.StompFrameHandler
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.web.socket.WebSocketHttpHeaders
import java.lang.reflect.Type
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

class RoomAcceptanceTest : AcceptanceTest() {
    @Test
    fun `SNS 계정 회원이 방을 생성한다`() {
        // given
        val `SNS 계정 로그인 응답` = `SNS 계정 로그인되어 있음`(`인증 요청값`())

        // when
        val response = `방 생성 요청`(`SNS 계정 로그인 응답`.token, `방 생성 요청값`())

        // then
        `방 생성됨`(response, `방 생성 요청값`())
    }

    @Test
    fun `익명 회원은 방을 생성할 수 없다`() {
        // given
        val `익명 로그인 응답` = `익명 로그인되어 있음`()

        // when
        val response = `방 생성 요청`(`익명 로그인 응답`.token, `방 생성 요청값`())

        // then
        `방 생성되지 않음`(response)
    }

    @Test
    fun `방에 이모지를 보낸다`() {
        // given
        val `SNS 계정 로그인 응답` = `SNS 계정 로그인되어 있음`(`인증 요청값`())
        val `익명 로그인 응답` = `익명 로그인되어 있음`()
        val 방 = `방 생성되어 있음`(`SNS 계정 로그인 응답`.token, `방 생성 요청값`())
        val `SNS 계정 세션` = `웹소켓 연결되어 있음`(port, `SNS 계정 로그인 응답`.token)
        val `익명 세션` = `웹소켓 연결되어 있음`(port, `익명 로그인 응답`.token)
        val futures = listOf(
            `방 구독되어 있음`(`SNS 계정 세션`, 방.roomId),
            `방 구독되어 있음`(`익명 세션`, 방.roomId))

        // when
        `이모지 보내기 요청`(`익명 세션`, 방.roomId, EmojiType.HEART)

        // then
        `이모지 받음`(futures, EmojiType.HEART)
    }

    @Test
    fun `익명 회원이 방 생성자에게 재생목록 항목 신청을 한다`() {
        // given
        val `SNS 계정 로그인 응답` = `SNS 계정 로그인되어 있음`(`인증 요청값`())
        val `익명 로그인 응답` = `익명 로그인되어 있음`()
        val 방 = `방 생성되어 있음`(`SNS 계정 로그인 응답`.token, `방 생성 요청값`())
        val `SNS 계정 세션` = `웹소켓 연결되어 있음`(port, `SNS 계정 로그인 응답`.token)
        val `익명 세션` = `웹소켓 연결되어 있음`(port, `익명 로그인 응답`.token)
        `방 구독되어 있음`(`SNS 계정 세션`, 방.roomId)
        `방 구독되어 있음`(`익명 세션`, 방.roomId)
        val future = `개인 큐 구독되어 있음`(`SNS 계정 세션`)
        val `방 재생목록 항목 신청 요청값` = `방 재생목록 항목 신청 요청값`(방.playlistId!!)

        // when
        `재생목록 항목 신청 요청`(`익명 세션`, 방.roomId, `방 재생목록 항목 신청 요청값`)

        // then
        `재생목록 항목 신청 요청 받음`(future, `방 재생목록 항목 신청 요청값`)
    }

    @Test
    fun `방 생성자가 재생목록 항목 신청을 승인한다`() {
        // given
        val `SNS 계정 로그인 응답` = `SNS 계정 로그인되어 있음`(`인증 요청값`())
        val `익명 로그인 응답` = `익명 로그인되어 있음`()
        val 방 = `방 생성되어 있음`(`SNS 계정 로그인 응답`.token, `방 생성 요청값`())
        val `SNS 계정 세션` = `웹소켓 연결되어 있음`(port, `SNS 계정 로그인 응답`.token)
        val `익명 세션` = `웹소켓 연결되어 있음`(port, `익명 로그인 응답`.token)
        val futures = listOf(
            `방 구독되어 있음`(`SNS 계정 세션`, 방.roomId),
            `방 구독되어 있음`(`익명 세션`, 방.roomId))
        val `신청된 재생목록 항목` = `재생목록 항목 신청되어 있음`(
            `익명 세션`,
            `SNS 계정 세션`,
            방.roomId,
            `방 재생목록 항목 신청 요청값`(방.playlistId!!))
        val `방 재생목록 항목 신청 승인 요청값` = `방 재생목록 항목 신청 승인 요청값`(
            `신청된 재생목록 항목`.playlistId,
            `신청된 재생목록 항목`.playlistItemId)

        // when
        `재생목록 항목 신청 승인 요청`(
            `SNS 계정 세션`,
            방.roomId,
            `방 재생목록 항목 신청 승인 요청값`)

        // then
        `재생목록 항목 신청 승인됨`(futures, `방 재생목록 항목 신청 승인 요청값`)
    }

    @Test
    fun `방 생성자가 아닐 경우 재생목록 항목 신청을 승인할 수 없다`() {
        // given
        val `SNS 계정 로그인 응답` = `SNS 계정 로그인되어 있음`(`인증 요청값`())
        val `익명 로그인 응답` = `익명 로그인되어 있음`()
        val 방 = `방 생성되어 있음`(`SNS 계정 로그인 응답`.token, `방 생성 요청값`())
        val `SNS 계정 세션` = `웹소켓 연결되어 있음`(port, `SNS 계정 로그인 응답`.token)
        val `익명 세션` = `웹소켓 연결되어 있음`(port, `익명 로그인 응답`.token)
        `방 구독되어 있음`(`SNS 계정 세션`, 방.roomId)
        `방 구독되어 있음`(`익명 세션`, 방.roomId)
        val future = `개인 큐 구독되어 있음`(`익명 세션`)
        val `신청된 재생목록 항목` = `재생목록 항목 신청되어 있음`(
            `익명 세션`,
            `SNS 계정 세션`,
            방.roomId,
            `방 재생목록 항목 신청 요청값`(방.playlistId!!))
        val `방 재생목록 항목 신청 승인 요청값` = `방 재생목록 항목 신청 승인 요청값`(
            `신청된 재생목록 항목`.playlistId,
            `신청된 재생목록 항목`.playlistItemId)

        // when
        `재생목록 항목 신청 승인 요청`(
            `익명 세션`,
            방.roomId,
            `방 재생목록 항목 신청 승인 요청값`)

        // then
        `재생목록 항목 신청 승인되지 않음`(future)
    }

    @Test
    fun `방 생성자가 재생목록 항목을 추가한다`() {
        // given
        val `SNS 계정 로그인 응답` = `SNS 계정 로그인되어 있음`(`인증 요청값`())
        val `익명 로그인 응답` = `익명 로그인되어 있음`()
        val 방 = `방 생성되어 있음`(`SNS 계정 로그인 응답`.token, `방 생성 요청값`())
        val `SNS 계정 세션` = `웹소켓 연결되어 있음`(port, `SNS 계정 로그인 응답`.token)
        val `익명 세션` = `웹소켓 연결되어 있음`(port, `익명 로그인 응답`.token)
        val futures = listOf(
            `방 구독되어 있음`(`SNS 계정 세션`, 방.roomId),
            `방 구독되어 있음`(`익명 세션`, 방.roomId))
        val `방 재생목록 항목 추가 요청값` = `방 재생목록 항목 추가 요청값`(방.playlistId!!)

        // when
        `재생목록 항목 추가 요청`(`SNS 계정 세션`, 방.roomId, `방 재생목록 항목 추가 요청값`)

        // then
        `재생목록 항목 추가됨`(futures, `방 재생목록 항목 추가 요청값`)
    }

    @Test
    fun `방 생성자가 아닐 경우 재생목록 항목을 추가할 수 없다`() {
        // given
        val `SNS 계정 로그인 응답` = `SNS 계정 로그인되어 있음`(`인증 요청값`())
        val `익명 로그인 응답` = `익명 로그인되어 있음`()
        val 방 = `방 생성되어 있음`(`SNS 계정 로그인 응답`.token, `방 생성 요청값`())
        val `SNS 계정 세션` = `웹소켓 연결되어 있음`(port, `SNS 계정 로그인 응답`.token)
        val `익명 세션` = `웹소켓 연결되어 있음`(port, `익명 로그인 응답`.token)
        `방 구독되어 있음`(`SNS 계정 세션`, 방.roomId)
        `방 구독되어 있음`(`익명 세션`, 방.roomId)
        val future = `개인 큐 구독되어 있음`(`익명 세션`)
        val `방 재생목록 항목 추가 요청값` = `방 재생목록 항목 추가 요청값`(방.playlistId!!)

        // when
        `재생목록 항목 추가 요청`(`익명 세션`, 방.roomId, `방 재생목록 항목 추가 요청값`)

        // then
        `재생목록 항목 추가되지 않음`(future)
    }

    companion object {
        fun `방 생성 요청`(token: String, request: RoomCreateRequest): ExtractableResponse<Response> {
            return RestAssured
                .given().log().all()
                .header(Header("Authorization", "Bearer $token"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .`when`().post("/api/v1/rooms")
                .then().log().all()
                .extract()
        }

        fun `방 생성됨`(
            response: ExtractableResponse<Response>,
            request: RoomCreateRequest,
        ) {
            val room = response.`as`(RoomDetailInfoResponse::class.java)

            assertAll(
                { assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()) },
                { assertThat(room.roomId).isNotNull() },
                { assertThat(room.description).isEqualTo(request.description) },
                { assertThat(room.playlistId).isNotNull() },
            )
        }

        fun `방 생성되지 않음`(response: ExtractableResponse<Response>) {
            assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value())
        }

        fun `방 생성되어 있음`(token: String, request: RoomCreateRequest): RoomDetailInfoResponse {
            return `방 생성 요청`(token, request).`as`(RoomDetailInfoResponse::class.java)
        }

        fun `웹소켓 연결되어 있음`(port: Int, token: String): StompSession {
            val stompHeaders = StompHeaders()
            stompHeaders.add("Authorization", "Bearer $token")

            return StompTestHelper.newClient()
                .connect("ws://localhost:${port}/${WS_ENDPOINT}",
                    WebSocketHttpHeaders(),
                    stompHeaders,
                    object : StompSessionHandlerAdapter() {})
                .get(5, TimeUnit.SECONDS)
        }

        fun `방 구독되어 있음`(session: StompSession, roomId: Long): CompletableFuture<WsResponse<*>> {
            val future: CompletableFuture<WsResponse<*>> = CompletableFuture()

            session.subscribe(
                "${WS_DESTINATION_PREFIX_TOPIC}/v1/rooms/${roomId}",
                object : StompFrameHandler {
                    override fun getPayloadType(headers: StompHeaders): Type {
                        return object : TypeReference<WsResponse<*>>() {}.type
                    }

                    override fun handleFrame(headers: StompHeaders, payload: Any?) {
                        future.complete(payload as WsResponse<*>)
                    }
                })

            return future
        }

        fun `이모지 보내기 요청`(session: StompSession, roomId: Long, emojiType: EmojiType) {
            session.send("${WS_APP_DESTINATION_PREFIX}/v1/rooms/${roomId}/send-emoji", RoomSendEmojiRequest(emojiType))
        }

        fun `이모지 받음`(futures: List<CompletableFuture<WsResponse<*>>>, emojiType: EmojiType) {
            val responses = futures
                .map { it.get(5, TimeUnit.SECONDS) }
                .map { JsonUtil.reDeserialize(it, object : TypeReference<WsResponse<RoomEmojiResponse>>() {}) }

            assertAll(
                { assertThat(responses.map { it.type }).allMatch { it == WsResponseType.EMOJI } },
                { assertThat(responses.map { it.data!!.emojiType }).allMatch { it == emojiType } },
            )
        }

        fun `개인 큐 구독되어 있음`(session: StompSession): CompletableFuture<WsResponse<*>> {
            val future: CompletableFuture<WsResponse<*>> = CompletableFuture()

            session.subscribe(
                "${WS_USER_DESTINATION_PREFIX}${WS_DESTINATION_PREFIX_QUEUE}",
                object : StompFrameHandler {
                    override fun getPayloadType(headers: StompHeaders): Type {
                        return object : TypeReference<WsResponse<*>>() {}.type
                    }

                    override fun handleFrame(headers: StompHeaders, payload: Any?) {
                        future.complete(payload as WsResponse<*>)
                    }
                })

            return future
        }

        fun `재생목록 항목 신청 요청`(session: StompSession, roomId: Long, request: RoomSendPlaylistItemRequestRequest) {
            session.send("${WS_APP_DESTINATION_PREFIX}/v1/rooms/${roomId}/send-playlist-item-request", request)
        }

        fun `재생목록 항목 신청 요청 받음`(
            future: CompletableFuture<WsResponse<*>>,
            request: RoomSendPlaylistItemRequestRequest,
        ): RoomPlaylistItemRequestResponse {
            val response = JsonUtil.reDeserialize(
                future.get(5, TimeUnit.SECONDS),
                object : TypeReference<WsResponse<RoomPlaylistItemRequestResponse>>() {})

            assertAll(
                { assertThat(response.type).isEqualTo(WsResponseType.PLAYLIST_ITEM_REQUEST) },
                { assertThat(response.data!!.playlistItemId).isNotNull() },
                { assertThat(response.data!!.playlistId).isEqualTo(request.playlistId) },
                { assertThat(response.data!!.videoId).isEqualTo(request.videoId) },
                { assertThat(response.data!!.title).isEqualTo(request.title) },
                { assertThat(response.data!!.thumbnail).isEqualTo(request.thumbnail) },
                { assertThat(response.data!!.duration).isEqualTo(request.duration) },
            )

            return response.data!!
        }

        fun `재생목록 항목 신청되어 있음`(
            itemRequestSession: StompSession,
            roomCreatorSession: StompSession,
            roomId: Long,
            request: RoomSendPlaylistItemRequestRequest,
        ): RoomPlaylistItemRequestResponse {
            `재생목록 항목 신청 요청`(itemRequestSession, roomId, request)
            return `재생목록 항목 신청 요청 받음`(`개인 큐 구독되어 있음`(roomCreatorSession), request)
        }

        fun `재생목록 항목 신청 승인 요청`(session: StompSession, roomId: Long, request: RoomAcceptPlaylistItemRequestRequest) {
            session.send("${WS_APP_DESTINATION_PREFIX}/v1/rooms/${roomId}/accept-playlist-item-request", request)
        }

        fun `재생목록 항목 신청 승인됨`(
            futures: List<CompletableFuture<WsResponse<*>>>, request: RoomAcceptPlaylistItemRequestRequest,
        ) {
            val responses = futures
                .map { it.get(5, TimeUnit.SECONDS) }
                .map {
                    JsonUtil.reDeserialize(it,
                        object : TypeReference<WsResponse<RoomPlaylistItemAddResponse>>() {})
                }

            assertAll(
                { assertThat(responses.map { it.type }).allMatch { it == WsResponseType.PLAYLIST_ITEM_ADD } },
                { assertThat(responses.map { it.data!!.playlistId }).allMatch { it == request.playlistId } },
                { assertThat(responses.map { it.data!!.playlistItemId }).allMatch { it == request.playlistItemId } },
            )
        }

        fun `재생목록 항목 신청 승인되지 않음`(futures: CompletableFuture<WsResponse<*>>) {
            val response = futures.get(5, TimeUnit.SECONDS)

            assertAll(
                { assertThat(response.type).isEqualTo(WsResponseType.ERROR) },
                { assertThat(response.code).isEqualTo(ErrorCode.FORBIDDEN.code) },
                { assertThat(response.message).isEqualTo(ErrorCode.FORBIDDEN.message) },
            )
        }

        fun `재생목록 항목 추가 요청`(session: StompSession, roomId: Long, request: RoomAddPlaylistItemRequest) {
            session.send("${WS_APP_DESTINATION_PREFIX}/v1/rooms/${roomId}/add-playlist-item", request)
        }

        fun `재생목록 항목 추가됨`(futures: List<CompletableFuture<WsResponse<*>>>, request: RoomAddPlaylistItemRequest) {
            val responses = futures
                .map { it.get(5, TimeUnit.SECONDS) }
                .map {
                    JsonUtil.reDeserialize(it,
                        object : TypeReference<WsResponse<RoomPlaylistItemAddResponse>>() {})
                }

            assertAll(
                { assertThat(responses.map { it.type }).allMatch { it == WsResponseType.PLAYLIST_ITEM_ADD } },
                { assertThat(responses.map { it.data!!.playlistId }).allMatch { it == request.playlistId } },
                { assertThat(responses.map { it.data!!.videoId }).allMatch { it == request.videoId } },
                { assertThat(responses.map { it.data!!.title }).allMatch { it == request.title } },
                { assertThat(responses.map { it.data!!.thumbnail }).allMatch { it == request.thumbnail } },
                { assertThat(responses.map { it.data!!.duration }).allMatch { it == request.duration } },
            )
        }
    }

    fun `재생목록 항목 추가되지 않음`(futures: CompletableFuture<WsResponse<*>>) {
        val response = futures.get(5, TimeUnit.SECONDS)

        assertAll(
            { assertThat(response.type).isEqualTo(WsResponseType.ERROR) },
            { assertThat(response.code).isEqualTo(ErrorCode.FORBIDDEN.code) },
            { assertThat(response.message).isEqualTo(ErrorCode.FORBIDDEN.message) },
        )
    }
}
