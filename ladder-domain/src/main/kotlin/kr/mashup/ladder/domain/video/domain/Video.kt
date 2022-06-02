package kr.mashup.ladder.domain.video.domain

import kr.mashup.ladder.domain.common.domain.BaseTimeEntity
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Video : BaseTimeEntity() {

    @Id
    val id: UUID = UUID.randomUUID()
}
