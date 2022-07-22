package kr.mashup.ladder.domain.room.infra.jpa

import kr.mashup.ladder.domain.room.domain.RoomMemberMapper
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 테스트 용도의 Repository
 */
interface RoomMemberMapperRepository : JpaRepository<RoomMemberMapper, Long>
