package kr.mashup.ladder.common.dto.response

import kr.mashup.ladder.domain.common.domain.BaseEntity
import java.time.LocalDateTime

open class BaseTimeResponse {

    lateinit var createdAt: LocalDateTime
    lateinit var updatedAt: LocalDateTime

    fun setBaseTime(entity: BaseEntity) {
        this.createdAt = entity.createdAt
        this.updatedAt = entity.updatedAt
    }

}
