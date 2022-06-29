package kr.mashup.ladder.config.session

import org.springframework.context.annotation.Configuration
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60 * 24 * 30) // 30일 만료
@Configuration
class RedisSessionConfig
