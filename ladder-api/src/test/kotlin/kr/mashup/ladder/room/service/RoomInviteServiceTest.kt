package kr.mashup.ladder.room.service

import kr.mashup.ladder.SetupMemberIntegrationTest
import kr.mashup.ladder.domain.room.domain.InvitationKey
import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.exception.RoomMemberConflictException
import kr.mashup.ladder.domain.room.domain.RoomRole
import kr.mashup.ladder.domain.room.infra.jpa.RoomMemberMapperRepository
import kr.mashup.ladder.domain.room.infra.jpa.RoomRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

internal class RoomInviteServiceTest(
    private val roomInviteService: RoomInviteService,
    private val roomRepository: RoomRepository,
    private val roomMemberMapperRepository: RoomMemberMapperRepository,
) : SetupMemberIntegrationTest() {

    @Test
    fun `초대장을 통해 방에 참여한다`() {
        // given
        val room = Room(
            description = "방에 대한 설명",
            invitationKey = InvitationKey.newInstance(),
        )
        roomRepository.save(room)

        // when
        roomInviteService.enterRoomByInvitationKey(room.invitationKey.invitationKey, memberId = member.id)

        // then
        val participants = roomMemberMapperRepository.findAll()
        assertThat(participants).hasSize(1)
        assertThat(participants[0].room.id).isEqualTo(room.id)
        assertThat(participants[0].memberId).isEqualTo(member.id)
        assertThat(participants[0].role).isEqualTo(RoomRole.GUEST)
    }

    @Test
    fun `방에 참여할때, 해당 방에 이미 참여자이면 Conflict 에러가 발생한다`() {
        // given
        val room = Room(
            description = "방에 대한 설명",
            invitationKey = InvitationKey.newInstance(),
        )
        room.addGuest(member.id)
        roomRepository.save(room)

        // when & then
        assertThatThrownBy {
            roomInviteService.enterRoomByInvitationKey(room.invitationKey.invitationKey, memberId = member.id)
        }.isInstanceOf(RoomMemberConflictException::class.java)
    }

    @Test
    fun `방에 참여할때, 해당 방에 이미 방장이면 Conflict 에러가 발생한다`() {
        // given
        val room = Room(
            description = "방에 대한 설명",
            invitationKey = InvitationKey.newInstance(),
        )
        room.addCreator(member.id)
        roomRepository.save(room)

        // when & then
        assertThatThrownBy {
            roomInviteService.enterRoomByInvitationKey(room.invitationKey.invitationKey, memberId = member.id)
        }.isInstanceOf(RoomMemberConflictException::class.java)
    }

}
