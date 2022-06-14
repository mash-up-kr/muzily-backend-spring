package kr.mashup.ladder.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.springframework.messaging.converter.DefaultContentTypeResolver
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.util.MimeTypeUtils
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import org.springframework.web.socket.sockjs.client.SockJsClient
import org.springframework.web.socket.sockjs.client.WebSocketTransport

class StompTestHelper {

    companion object {
        private val converter: MappingJackson2MessageConverter = MappingJackson2MessageConverter()

        init {
            val resolver = DefaultContentTypeResolver()
            resolver.defaultMimeType = MimeTypeUtils.APPLICATION_JSON
            converter.contentTypeResolver = resolver
            converter.objectMapper = ObjectMapper().registerModule(kotlinModule())
        }

        fun newClient(): WebSocketStompClient {
            val sockJsClient = SockJsClient(listOf(WebSocketTransport(StandardWebSocketClient())))
            val stompClient = WebSocketStompClient(sockJsClient)
            stompClient.messageConverter = converter
            return stompClient
        }
    }
}
