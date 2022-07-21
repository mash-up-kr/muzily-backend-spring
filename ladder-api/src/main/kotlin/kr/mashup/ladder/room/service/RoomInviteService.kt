package kr.mashup.ladder.room.service

import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.RoomNotFoundException
import kr.mashup.ladder.domain.room.infra.jpa.RoomRepository
import kr.mashup.ladder.room.dto.response.RoomInfoResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomInviteService(
    private val roomRepository: RoomRepository,
) {

    @Transactional(readOnly = true)
    fun getByInvitationKey(invitationKey: String, memberId: Long): RoomInfoResponse {
        val room = findRoomByInvitationKey(invitationKey)
        return RoomInfoResponse.from(room = room, memberId = memberId)
    }

    @Transactional
    fun enterRoomByInvitationKey(invitationKey: String, memberId: Long) {
        val room = findRoomByInvitationKey(invitationKey)
        room.addGuest(memberId)
    }

    private fun findRoomByInvitationKey(invitationKey: String): Room {
        return roomRepository.findRoomByInvitationKey(invitationKey)
            ?: throw RoomNotFoundException("해당하는 초대장(${invitationKey})에 해당하는 방은 존재하지 않습니다")
    }

}
