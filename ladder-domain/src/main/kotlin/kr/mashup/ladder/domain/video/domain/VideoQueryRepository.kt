package kr.mashup.ladder.domain.video.domain

interface VideoQueryRepository {

    fun findAll(): List<Video>
}
