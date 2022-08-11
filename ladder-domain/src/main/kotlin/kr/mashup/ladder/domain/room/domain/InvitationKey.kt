package kr.mashup.ladder.domain.room.domain

import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class InvitationKey(
    @Column(nullable = false)
    val invitationKey: String,
) {

    companion object {
        private const val INVITATION_PREFIX = "ladder-v1"

        fun newInstance(): InvitationKey {
            return InvitationKey(
                invitationKey = "${INVITATION_PREFIX}-${UUID.randomUUID()}"
            )
        }
    }

}
