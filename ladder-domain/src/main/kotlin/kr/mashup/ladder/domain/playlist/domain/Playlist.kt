package kr.mashup.ladder.domain.playlist.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItem
import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItemStatus
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Table(name = "playlist")
@Entity
class Playlist(
    val roomId: Long = 0,
) : BaseEntity() {
    @OneToMany(mappedBy = "playlist")
    val items: List<PlaylistItem> = listOf()

    fun getPendingItems(): List<PlaylistItem> {
        return items.filter { it.status == PlaylistItemStatus.PENDING }
    }

    fun getAcceptedItems(): List<PlaylistItem> {
        return items.filter { it.status == PlaylistItemStatus.ACCEPTED }
    }
}
