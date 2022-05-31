package kr.mashup.ladder.domain.video.domain

interface VideoRepository {

    fun save(video: Video): Video
}
