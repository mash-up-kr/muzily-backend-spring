package kr.mashup.ladder.domain.mood.infra.jpa

import kr.mashup.ladder.domain.mood.domain.MoodSuggestion
import kr.mashup.ladder.domain.mood.infra.querydsl.MoodSuggestionQueryRepository
import org.springframework.data.jpa.repository.JpaRepository

interface MoodSuggestionRepository : JpaRepository<MoodSuggestion, Long>, MoodSuggestionQueryRepository
