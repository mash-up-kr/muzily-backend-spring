package kr.mashup.ladder.domain.playlist.domain

import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
class PlayInformation {
    var currentItemId: Long? = null
        protected set

    @Enumerated(EnumType.STRING)
    var playStatus: PlayStatus? = null
        protected set

    fun update(currentItemId: Long, playStatus: PlayStatus) {
        this.currentItemId = currentItemId
        this.playStatus = playStatus
    }
}
