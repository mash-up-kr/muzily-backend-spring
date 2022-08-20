package kr.mashup.ladder.domain.playlist.domain

import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class PlayInformation(
    val currentItemId: Long,
    @Enumerated(EnumType.STRING)
    val playStatus: PlayStatus,
) {
    companion object {
        fun empty(): PlayInformation {
            return PlayInformation(
                currentItemId = -1,
                playStatus = PlayStatus.PAUSE
            )
        }
    }
}
