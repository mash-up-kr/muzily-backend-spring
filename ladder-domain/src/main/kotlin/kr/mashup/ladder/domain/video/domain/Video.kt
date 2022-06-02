package kr.mashup.ladder.domain.video.domain

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Video {

    @Id
    val id: UUID = UUID.randomUUID()
}
