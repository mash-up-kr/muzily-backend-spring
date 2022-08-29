package kr.mashup.ladder.config.webclient

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.util.concurrent.TimeUnit

private const val READ_TIME_OUT_MS = 3000L
private const val WRITE_TIME_OUT_MS = 3000L
private const val CONNECTION_TIME_OUT_MS = 3000

@Configuration
class WebClientConfig {

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder()
            .clientConnector(
                ReactorClientHttpConnector(
                    HttpClient.create(
                        ConnectionProvider.builder("ladder-search-webclient")
                            .build()
                    )
                        .doOnConnected { conn ->
                            conn
                                .addHandlerLast(ReadTimeoutHandler(READ_TIME_OUT_MS, TimeUnit.MILLISECONDS))
                                .addHandlerLast(WriteTimeoutHandler(WRITE_TIME_OUT_MS, TimeUnit.MILLISECONDS))
                        }
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECTION_TIME_OUT_MS)
                )
            )
            .build()
    }

}
