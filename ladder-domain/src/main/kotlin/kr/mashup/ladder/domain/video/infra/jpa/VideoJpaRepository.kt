package kr.mashup.ladder.domain.video.infra.jpa

import kr.mashup.ladder.domain.video.domain.Video
import kr.mashup.ladder.domain.video.domain.VideoRepository
import org.springframework.data.jpa.repository.JpaRepository

@Deprecated(message = "샘플 코드")
interface VideoJpaRepository : JpaRepository<Video, String>, VideoRepository
