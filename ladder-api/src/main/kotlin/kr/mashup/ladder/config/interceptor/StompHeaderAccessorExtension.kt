package kr.mashup.ladder.config.interceptor

import kr.mashup.ladder.config.resolver.MEMBER_ID
import kr.mashup.ladder.config.ws.WS_DESTINATION_PREFIX_TOPIC
import org.springframework.http.HttpHeaders
import org.springframework.messaging.simp.stomp.StompHeaderAccessor

private const val AUTHORIZATION_HEADER_KEY = HttpHeaders.AUTHORIZATION
private const val AUTHORIZATION_HEADER_VALUE_PREFIX = "Bearer "
private const val MEMBER_ID_SESSION_ATTRIBUTE_KEY = MEMBER_ID

fun StompHeaderAccessor.getSessionIdFromHeader(): String {
    val header = this.getFirstNativeHeader(AUTHORIZATION_HEADER_KEY)
    if (header.isNullOrBlank() || !header.startsWith(AUTHORIZATION_HEADER_VALUE_PREFIX)) {
        throw IllegalArgumentException("잘못된 Authorization Header (${header})가 요청되었습니다")
    }

    return header.split(AUTHORIZATION_HEADER_VALUE_PREFIX)[1]
}

fun StompHeaderAccessor.setMemberIdFromSessionAttributes(memberId: Long) {
    if (this.sessionAttributes != null) {
        this.sessionAttributes!![MEMBER_ID_SESSION_ATTRIBUTE_KEY] = memberId
    } else {
        this.sessionAttributes = mutableMapOf<String, Any>(Pair(MEMBER_ID_SESSION_ATTRIBUTE_KEY, memberId))
    }
}

fun StompHeaderAccessor.getMemberIdFromSessionAttributes(): Long {
    return sessionAttributes?.get(MEMBER_ID_SESSION_ATTRIBUTE_KEY) as Long
}

fun StompHeaderAccessor.isDestinationRoom(): Boolean {
    return this.destination?.matches(Regex("^$WS_DESTINATION_PREFIX_TOPIC/v1/rooms/\\d+$")) ?: false
}

fun StompHeaderAccessor.getRoomId(): Long {
    val roomIdStr =
        this.destination?.replace("${WS_DESTINATION_PREFIX_TOPIC}/v1/rooms/", "") ?: throw IllegalArgumentException()
    return roomIdStr.toLong()
}
