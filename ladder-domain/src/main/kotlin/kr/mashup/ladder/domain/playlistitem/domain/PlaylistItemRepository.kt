package kr.mashup.ladder.domain.playlistitem.domain

import org.springframework.data.jpa.repository.JpaRepository

interface PlaylistItemRepository : JpaRepository<PlaylistItem, Long> {
}
