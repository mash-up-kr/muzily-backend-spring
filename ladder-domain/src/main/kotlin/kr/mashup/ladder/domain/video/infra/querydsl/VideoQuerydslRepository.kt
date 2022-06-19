package kr.mashup.ladder.domain.video.infra.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.mashup.ladder.domain.video.domain.QVideo
import kr.mashup.ladder.domain.video.domain.Video
import kr.mashup.ladder.domain.video.domain.VideoQueryRepository
import org.springframework.stereotype.Repository

@Repository
class VideoQuerydslRepository(
    val jpaQueryFactory: JPAQueryFactory,
) : VideoQueryRepository {

    override fun findAll(): List<Video> {
        return jpaQueryFactory.selectFrom(QVideo.video).fetch()
    }
}
