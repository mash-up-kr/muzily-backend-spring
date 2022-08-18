package kr.mashup.ladder.room.dto.response

import kr.mashup.ladder.domain.room.domain.InvitationKey

data class RoomInviteInfoResponse(
    val invitationKey: String,
) {

    companion object {
        fun of(invitationKey: InvitationKey) = RoomInviteInfoResponse(
            invitationKey = invitationKey.invitationKey,
        )
    }

}
