package kr.mashup.ladder.auth.external.dto.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "kakao.profile")
@ConstructorBinding
data class KaKaoUserProperties(
    val url: String,
)
