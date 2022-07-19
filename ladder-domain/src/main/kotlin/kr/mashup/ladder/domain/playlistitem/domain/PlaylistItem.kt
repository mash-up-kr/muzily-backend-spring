package kr.mashup.ladder.domain.playlistitem.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import kr.mashup.ladder.domain.playlist.domain.Playlist
import javax.persistence.*

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
