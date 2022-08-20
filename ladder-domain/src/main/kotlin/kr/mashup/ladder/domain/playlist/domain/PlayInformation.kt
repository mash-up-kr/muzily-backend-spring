package kr.mashup.ladder.domain.playlist.domain

import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class PlayInformation(
    val currentItemId: Long? = null,
    @Enumerated(EnumType.STRING)
    val playStatus: PlayStatus? = null,
)
