package kr.mashup.ladder.domain.video.domain

@Deprecated(message = "샘플 코드")
interface VideoRepository {

    fun save(video: Video): Video
}
