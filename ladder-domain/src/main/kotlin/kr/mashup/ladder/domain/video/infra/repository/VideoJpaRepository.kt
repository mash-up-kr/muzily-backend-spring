package kr.mashup.ladder.domain.video.infra.repository

import kr.mashup.ladder.domain.video.domain.Video
import kr.mashup.ladder.domain.video.domain.VideoRepository
import org.springframework.data.jpa.repository.JpaRepository

interface VideoJpaRepository : JpaRepository<Video, String>, VideoRepository
