package kr.mashup.ladder.domain.playlistitem.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import kr.mashup.ladder.domain.playlist.domain.Playlist
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Table(name = "playlist_item")
@Entity
class PlaylistItem(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id")
    val playlist: Playlist,
    videoId: String,
    title: String,
    thumbnail: String,
    duration: Int,
    dominantColor: String?,
) : BaseEntity() {
    var videoId: String = videoId
        protected set
    var title: String = title
        protected set
    var thumbnail: String = thumbnail
        protected set
    var duration: Int = duration
        protected set
    var dominantColor: String? = dominantColor
        protected set

    @Enumerated(value = EnumType.STRING)
    var status: PlaylistItemStatus = PlaylistItemStatus.PENDING
        protected set

    fun accept() {
        status = PlaylistItemStatus.ACCEPTED
    }
}
