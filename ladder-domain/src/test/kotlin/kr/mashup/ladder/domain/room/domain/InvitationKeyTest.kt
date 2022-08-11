package kr.mashup.ladder.domain.room.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class InvitationKeyTest {

    @Test
    fun `초대장을 생성합니다`() {
        // when
        val sut = InvitationKey.newInstance()

        // then
        assertThat(sut.invitationKey).isNotNull
        assertThat(sut.invitationKey).startsWith("ladder-v1")
        assertThat(sut.invitationKey).hasSize(46)
    }

}
