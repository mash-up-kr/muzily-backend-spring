package kr.mashup.ladder.external.kakao.dto.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "kakao.token")
@ConstructorBinding
data class KaKaoTokenProperties(
    val url: String,
    val clientId: String,
    val clientSecret: String,
    val grantType: String
)
