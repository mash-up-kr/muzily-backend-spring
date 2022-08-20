package kr.mashup.ladder.domain.room.infra.querydsl

import kr.mashup.ladder.domain.room.domain.Room

interface RoomQueryRepository {

    fun findRoomByInvitationKey(invitationKey: String): Room?

    fun existsRoomByMemberId(memberId: Long): Boolean

    fun findRoomIdByCreatorId(creatorId: Long): Long?

    fun findRoomById(roomId: Long): Room?

    fun findRoomByIdWithFetchMember(roomId: Long): Room?

    fun existsRoomByIdAndCreatorId(roomId: Long, creatorId: Long): Boolean

    fun existsRoomByIdAndMemberId(roomId: Long, creatorId: Long): Boolean

}
