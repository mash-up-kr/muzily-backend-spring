package kr.mashup.ladder.domain.mood.infra.jpa

import kr.mashup.ladder.domain.mood.domain.MoodRecommend
import kr.mashup.ladder.domain.mood.infra.querydsl.MoodRecommendQueryRepository
import org.springframework.data.jpa.repository.JpaRepository

interface MoodRecommendRepository : JpaRepository<MoodRecommend, Long>, MoodRecommendQueryRepository
