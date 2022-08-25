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

    @Column(name = "`order`", columnDefinition = "TEXT")
    @Convert(converter = OrderConverter::class)
    val order: MutableList<Long> = mutableListOf()

    @Embedded
    var playInformation: PlayInformation = PlayInformation.empty()
        protected set

    fun getPendingItems(): List<PlaylistItem> {
        return items.filter { it.status == PlaylistItemStatus.PENDING }
            .sortedBy { it.createdAt }
    }

    fun getAcceptedItems(): List<PlaylistItem> {
        val itemById = items.filter { it.status == PlaylistItemStatus.ACCEPTED }
            .associateBy { it.id }
        return order.mapNotNull { itemById[it] }
    }

    fun addToOrder(item: PlaylistItem) {
        order.add(item.id)
    }

    fun removeFromOrder(itemId: Long) {
        order.removeIf { it == itemId }
    }

    fun removeFromOrderBulk(itemIds: List<Long>) {
        for (itemId in itemIds) {
            removeFromOrder(itemId)
        }
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
        playInformation = PlayInformation(currentItemId, playStatus)
    }
}
