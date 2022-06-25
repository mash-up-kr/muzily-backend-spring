package kr.mashup.ladder.domain.video.domain

@Deprecated(message = "샘플 코드")
interface VideoQueryRepository {

    fun findAll(): List<Video>
}
