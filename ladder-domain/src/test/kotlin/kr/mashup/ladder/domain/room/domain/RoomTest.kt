package kr.mashup.ladder.domain.room.domain

import kr.mashup.ladder.domain.room.domain.emoji.EmojiType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

internal class RoomTest {
    @Test
    fun `방을 생성한다`() {
        // given
        val name = "코딩할때 듣기 좋은 노래"

        // when
        val room = Room.newInstance(name = name, memberId = 1L, emojiType = EmojiType.BOOK)

        // then
        assertAll(
            { Assertions.assertThat(room).isNotNull },
            { Assertions.assertThat(room.name).isEqualTo(name) }
        )
    }
}
