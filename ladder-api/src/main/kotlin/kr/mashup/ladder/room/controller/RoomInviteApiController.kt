package kr.mashup.ladder.room.controller

import io.swagger.annotations.ApiOperation
import kr.mashup.ladder.config.annotation.Auth
import kr.mashup.ladder.config.annotation.MemberId
import kr.mashup.ladder.common.constants.ApiResponseConstants
import kr.mashup.ladder.room.dto.response.RoomInfoResponse
import kr.mashup.ladder.room.service.RoomInviteService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RoomInviteApiController(
    private val roomInviteService: RoomInviteService,
) {

    @ApiOperation("방에 대한 초대장 정보를 조회합니다")
    @Auth
    @GetMapping("/api/v1/rooms/invitation/{invitationKey}")
    fun getRoomInvitationInfo(
        @PathVariable invitationKey: String,
        @MemberId memberId: Long,
    ): RoomInfoResponse {
        return roomInviteService.getByInvitationKey(invitationKey = invitationKey, memberId = memberId)
    }

    @ApiOperation("초대장을 통해 방에 대해 입장합니다")
    @Auth
    @PutMapping("/api/v1/rooms/invitation/{invitationKey}")
    fun enterRoomByInvitationKey(
        @PathVariable invitationKey: String,
        @MemberId memberId: Long,
    ): String {
        roomInviteService.enterRoomByInvitationKey(invitationKey = invitationKey, memberId = memberId)
        return ApiResponseConstants.SUCCESS
    }

}
