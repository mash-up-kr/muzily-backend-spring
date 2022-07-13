package kr.mashup.ladder.domain.playlist.domain

import org.springframework.data.jpa.repository.JpaRepository

interface PlaylistRepository : JpaRepository<Playlist, Long> {
    fun findByRoomId(roomId: Long): Playlist
    fun findByRoomIdIn(roomIds: List<Long>): List<Playlist>
}
