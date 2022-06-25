package kr.mashup.ladder.domain.video.domain

import kr.mashup.ladder.domain.common.domain.BaseTimeEntity
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Deprecated(message = "샘플 코드")
@Entity
class Video : BaseTimeEntity() {

    @Id
    val id: UUID = UUID.randomUUID()
}
