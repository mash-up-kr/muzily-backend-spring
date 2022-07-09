package kr.mashup.ladder.domain.room.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class RoomTest {
    @Test
    fun `방을 생성한다`() {
        // given
        val description = "코딩할때 듣기 좋은 노래"

        // when
        val room = Room.newInstance(description = description, memberId = 1L)

        // then
        assertAll(
            { Assertions.assertThat(room).isNotNull },
            { Assertions.assertThat(room.description).isEqualTo(description) }
        )
    }
}
