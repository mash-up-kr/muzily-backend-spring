package kr.mashup.ladder.room.service

import kr.mashup.ladder.SetupMemberIntegrationTest
import kr.mashup.ladder.domain.common.exception.model.ForbiddenException
import kr.mashup.ladder.domain.playlist.domain.Playlist
import kr.mashup.ladder.domain.playlist.domain.PlaylistRepository
import kr.mashup.ladder.domain.room.domain.InvitationKey
import kr.mashup.ladder.domain.room.domain.Room
import kr.mashup.ladder.domain.room.domain.RoomRole
import kr.mashup.ladder.domain.room.domain.RoomStatus
import kr.mashup.ladder.domain.room.domain.emoji.EmojiType
import kr.mashup.ladder.domain.room.exception.RoomConflictException
import kr.mashup.ladder.domain.room.exception.RoomNotFoundException
import kr.mashup.ladder.domain.room.infra.jpa.RoomMemberMapperRepository
import kr.mashup.ladder.domain.room.infra.jpa.RoomRepository
import kr.mashup.ladder.room.dto.request.RoomCreateRequest
import kr.mashup.ladder.room.dto.request.RoomUpdateRequest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

internal class RoomServiceTest(
    private val roomService: RoomService,
    private val roomRepository: RoomRepository,
    private val roomMemberMapperRepository: RoomMemberMapperRepository,
    private val playlistRepository: PlaylistRepository,
) : SetupMemberIntegrationTest() {

    @Test
    fun `새로운 방을 생성한다`() {
        // given
        val request = RoomCreateRequest(name = "방에 대한 설명", emojiType = EmojiType.BOOK)

        // when
        roomService.create(request, memberId = member.id)

        // then
        val rooms = roomRepository.findAll()
        assertThat(rooms).hasSize(1)
        assertRoom(room = rooms[0], name = request.name)
        assertThat(rooms[0].invitationKey).isNotNull
        assertThat(rooms[0].emojiType).isEqualTo(request.emojiType)
    }

    @Test
    fun `새로운 방을 생성하면 해당 멤버는 방의 방장이 된다`() {
        // given
        val request = RoomCreateRequest(name = "방에 대한 설명", emojiType = EmojiType.BOOK)

        // when
        roomService.create(request, memberId = member.id)

        // then
        val rooms = roomRepository.findAll()
        assertThat(rooms).hasSize(1)

        val participants = roomMemberMapperRepository.findAll()
        assertThat(participants).hasSize(1)
        assertThat(participants[0].room.id).isEqualTo(rooms[0].id)
        assertThat(participants[0].memberId).isEqualTo(member.id)
        assertThat(participants[0].role).isEqualTo(RoomRole.CREATOR)
    }

    @Test
    fun `방을 생성할때, 방에 플레이리스트가 생성된다`() {
        // given
        val request = RoomCreateRequest(
            name = "방에 대한 설명",
            emojiType = EmojiType.BOOK,
        )

        // when
        roomService.create(request, memberId = member.id)

        // then
        val playlists = playlistRepository.findAll()
        assertThat(playlists).hasSize(1)
        assertThat(playlists[0].roomId).isNotNull
    }

    @Test
    fun `하나의 계정으로 하나의 방만 생성할 수 있다`() {
        // given
        val room = Room(
            name = "방에 대한 설명",
            invitationKey = InvitationKey.newInstance(),
            emojiType = EmojiType.BOOK,
        )
        room.addCreator(member.id)
        roomRepository.save(room)

        val request = RoomCreateRequest(
            name = "방에 대한 설명",
            emojiType = EmojiType.BOOK,
        )

        // when & then
        assertThatThrownBy {
            roomService.create(request, memberId = member.id)
        }.isInstanceOf(RoomConflictException::class.java)
    }

    @Test
    fun `방에 대한 정보를 수정합니다`() {
        // given
        val room = Room(
            name = "방에 대한 설명",
            invitationKey = InvitationKey.newInstance(),
            emojiType = EmojiType.BOOK,
        )
        room.addCreator(member.id)
        val savedRoom = roomRepository.save(room)
        playlistRepository.save(Playlist(savedRoom.id))

        val request = RoomUpdateRequest(
            name = "변경 된 방에 대한 설명",
            emojiType = EmojiType.BOOK,
        )

        // when
        roomService.update(roomId = room.id, request = request, memberId = member.id)

        // then
        val rooms = roomRepository.findAll()
        assertThat(rooms).hasSize(1)
        assertRoom(room = rooms[0], name = request.name)
        assertThat(rooms[0].invitationKey).isNotNull
        assertThat(rooms[0].emojiType).isEqualTo(request.emojiType)
    }

    @Test
    fun `존재하지 않는 방에 대해서 정보를 수정할 수 없다`() {
        // given
        val notFoundRoomId = -1L

        val request = RoomUpdateRequest(
            name = "변경 된 방에 대한 설명",
            emojiType = EmojiType.BOOK,
        )

        // when & then
        assertThatThrownBy {
            roomService.update(roomId = notFoundRoomId, request = request, memberId = member.id)
        }.isInstanceOf(RoomNotFoundException::class.java)
    }

    @Test
    fun `방장만이 방에 대한 정보를 수정할 수 있다`() {
        // given
        val isNotOwnerId = -1L

        val room = Room(
            name = "방에 대한 설명",
            invitationKey = InvitationKey.newInstance(),
            emojiType = EmojiType.BOOK,
        )
        room.addCreator(member.id)
        roomRepository.save(room)

        val request = RoomUpdateRequest(
            name = "변경 된 방에 대한 설명",
            emojiType = EmojiType.BOOK,
        )

        // when & then
        assertThatThrownBy {
            roomService.update(roomId = room.id, request = request, memberId = isNotOwnerId)
        }.isInstanceOf(ForbiddenException::class.java)
    }

    @Test
    fun `삭제된 가게에 대해서 수정할 수 없다`() {
        // given
        val room = Room(
            name = "방에 대한 설명",
            invitationKey = InvitationKey.newInstance(),
            status = RoomStatus.DELETED,
            emojiType = EmojiType.BOOK,
        )
        room.addCreator(member.id)
        roomRepository.save(room)

        val request = RoomUpdateRequest(name = "변경 된 방에 대한 설명", emojiType = EmojiType.BOOK)

        // when & then
        assertThatThrownBy {
            roomService.update(roomId = room.id, request = request, memberId = member.id)
        }.isInstanceOf(RoomNotFoundException::class.java)
    }

    @Test
    fun `방을 삭제한다`() {
        // given
        val room = Room(
            name = "방에 대한 설명",
            invitationKey = InvitationKey.newInstance(),
            emojiType = EmojiType.BOOK,
        )
        room.addCreator(member.id)
        roomRepository.save(room)

        // when
        roomService.deleteRoom(roomId = room.id, memberId = member.id)

        // then
        val rooms = roomRepository.findAll()
        assertThat(rooms).hasSize(1)
        assertThat(rooms[0].status).isEqualTo(RoomStatus.DELETED)
        assertRoom(room = rooms[0], name = room.name)
        assertThat(rooms[0].invitationKey).isNotNull
    }

    @Test
    fun `방장만이 방에 대한 정보를 삭제할 수 있다`() {
        // given
        val isNotOwnerId = -1L

        val room = Room(
            name = "방에 대한 설명",
            invitationKey = InvitationKey.newInstance(),
            emojiType = EmojiType.BOOK,
        )
        room.addCreator(member.id)
        roomRepository.save(room)

        // when & then
        assertThatThrownBy {
            roomService.deleteRoom(roomId = room.id, memberId = isNotOwnerId)
        }.isInstanceOf(ForbiddenException::class.java)
    }

    @Test
    fun `삭제된 가게에 대해서 삭제할 수 없다`() {
        // given
        val room = Room(
            name = "방에 대한 설명",
            invitationKey = InvitationKey.newInstance(),
            status = RoomStatus.DELETED,
            emojiType = EmojiType.BOOK,
        )
        room.addCreator(member.id)
        roomRepository.save(room)

        // when & then
        assertThatThrownBy {
            roomService.deleteRoom(roomId = room.id, memberId = member.id)
        }.isInstanceOf(RoomNotFoundException::class.java)
    }

    private fun assertRoom(room: Room, name: String) {
        assertThat(room.name).isEqualTo(name)
    }

}
