package kr.mashup.ladder.domain.playlist.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItem
import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItemStatus
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Table(name = "playlist")
@Entity
class Playlist(
    val roomId: Long,
) : BaseEntity() {
    @OneToMany
    @JoinColumn(name = "playlistId")
    val playlistItems: List<PlaylistItem> = listOf()

    fun getAcceptedItems(): List<PlaylistItem> {
        return playlistItems.filter { it.status == PlaylistItemStatus.ACCEPTED }
    }
}
