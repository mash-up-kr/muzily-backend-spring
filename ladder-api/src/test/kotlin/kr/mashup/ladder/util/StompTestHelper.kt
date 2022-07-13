package kr.mashup.ladder.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient

class StompTestHelper {

    companion object {
        private val converter = MappingJackson2MessageConverter()

        init {
            converter.objectMapper = ObjectMapper().registerModule(kotlinModule())
        }

        fun newClient(): WebSocketStompClient {
            val webSocketClient = StandardWebSocketClient()
            val stompClient = WebSocketStompClient(webSocketClient)
            stompClient.messageConverter = converter
            return stompClient
        }
    }
}
