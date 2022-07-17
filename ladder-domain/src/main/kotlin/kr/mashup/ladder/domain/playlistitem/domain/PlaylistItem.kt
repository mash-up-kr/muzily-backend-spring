package kr.mashup.ladder.domain.playlistitem.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

@Table(name = "playlist_item")
@Entity
class PlaylistItem(
    val playlistId: Long,
    videoId: String,
    title: String,
    thumbnail: String,
    duration: Int,
) : BaseEntity() {
    var videoId: String = videoId
        protected set
    var title: String = title
        protected set
    var thumbnail: String = thumbnail
        protected set
    var duration: Int = duration
        protected set
    @Enumerated(value = EnumType.STRING)
    var status: PlaylistItemStatus = PlaylistItemStatus.PENDING
        protected set
}
