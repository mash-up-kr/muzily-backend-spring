package kr.mashup.ladder.domain.video.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class VideoTest {

    @Test
    fun `비디오를 생성한다`() {
        // given

        // when
        val video = Video()

        // then
        assertAll(
            { assertThat(video).isNotNull },
            { assertThat(video.id).isNotNull() }
        )
    }
}
