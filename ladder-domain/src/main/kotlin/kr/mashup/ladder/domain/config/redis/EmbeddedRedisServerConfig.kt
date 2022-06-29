package kr.mashup.ladder.domain.config.redis

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import redis.embedded.RedisServer
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy


@Configuration
@Profile(value = ["local", "test"])
class EmbeddedRedisServerConfig(
    @Value("\${spring.redis.port}") private val port: Int,
) {

    companion object {
        private var redisServer: RedisServer? = null
    }

    @PostConstruct
    fun start() {
        if (redisServer == null || !redisServer?.isActive!!) {
            redisServer = RedisServer(port)
            redisServer!!.start()
        }
    }

    @PreDestroy
    fun stop() {
        redisServer?.stop()
    }
}
