package kr.mashup.ladder.domain.room.infra.querydsl

import kr.mashup.ladder.domain.room.domain.RoomMood

interface RoomMoodQueryRepository {

    fun findAllByInRoomIds(roomIds: List<Long>): List<RoomMood>

}
