package kr.mashup.ladder.domain.playlist.domain

import org.springframework.data.jpa.repository.JpaRepository

interface PlaylistRepository : JpaRepository<Playlist, Long>
