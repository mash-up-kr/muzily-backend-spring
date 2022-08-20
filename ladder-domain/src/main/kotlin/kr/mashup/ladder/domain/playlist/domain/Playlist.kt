package kr.mashup.ladder.domain.playlist.domain

import kr.mashup.ladder.domain.common.domain.BaseEntity
import kr.mashup.ladder.domain.playlist.infra.converter.OrderConverter
import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItem
import kr.mashup.ladder.domain.playlistitem.domain.PlaylistItemStatus
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Table(name = "playlist")
@Entity
class Playlist(
    val roomId: Long,
) : BaseEntity() {
    @OneToMany(mappedBy = "playlist")
    val items: List<PlaylistItem> = listOf()

    @Column(name = "`order`")
    @Convert(converter = OrderConverter::class)
    val order: MutableList<Long> = mutableListOf()

    @Embedded
    val playInformation: PlayInformation = PlayInformation()

    fun getPendingItems(): List<PlaylistItem> {
        return items.filter { it.status == PlaylistItemStatus.PENDING }
    }

    fun getAcceptedItems(): List<PlaylistItem> {
        return items.filter { it.status == PlaylistItemStatus.ACCEPTED }
    }

    fun addToOrder(item: PlaylistItem) {
        order.add(item.id)
    }

    fun removeFromOrder(itemId: Long) {
        order.removeIf { it == itemId }
    }

    fun changeOrder(itemId: Long, prevItemIdToMove: Long?) {
        if (order.isEmpty() || order.find { it == itemId } == null) {
            return
        }

        order.removeIf { it == itemId }
        if (prevItemIdToMove == null) {
            order.add(0, itemId)
            return
        }

        order.add(order.indexOf(prevItemIdToMove) + 1, itemId)
    }

    fun updatePlayInformation(currentItemId: Long, playStatus: PlayStatus) {
        playInformation.update(currentItemId, playStatus)
    }
}
