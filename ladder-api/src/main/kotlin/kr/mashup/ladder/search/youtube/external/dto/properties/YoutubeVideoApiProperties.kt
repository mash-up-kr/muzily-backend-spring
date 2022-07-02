package kr.mashup.ladder.search.youtube.external.dto.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "youtube.video")
@ConstructorBinding
data class YoutubeVideoApiProperties(
    val url: String,
    val key: String,
    val part: String,
)
