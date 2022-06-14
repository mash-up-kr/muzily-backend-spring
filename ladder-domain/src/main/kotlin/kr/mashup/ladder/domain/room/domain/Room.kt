package kr.mashup.ladder.domain.room.domain

import kr.mashup.ladder.domain.common.domain.BaseTimeEntity
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tb_room")
class Room(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val roomId: Long = 0L,
    val description: String,
) : BaseTimeEntity()
