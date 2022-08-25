package kr.mashup.ladder.room.controller

import io.swagger.annotations.ApiOperation
import kr.mashup.ladder.config.annotation.Auth
import kr.mashup.ladder.config.annotation.MemberId
import kr.mashup.ladder.common.constants.ApiResponseConstants.SUCCESS
import kr.mashup.ladder.room.dto.request.RoomCreateRequest
import kr.mashup.ladder.room.dto.request.RoomUpdateRequest
import kr.mashup.ladder.room.dto.response.CreatedRoomResponse
import kr.mashup.ladder.room.dto.response.RoomDetailInfoResponse
import kr.mashup.ladder.room.service.RoomService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class RoomApiController(
    private val roomService: RoomService,
) {

    @ApiOperation("방을 생성합니다 (계정에 연결된 사용자만 가능)")
    @Auth(allowedAnonymous = false)
    @PostMapping("/api/v1/rooms")
    fun createRoom(
        @Valid @RequestBody request: RoomCreateRequest,
        @MemberId memberId: Long,
    ): RoomDetailInfoResponse {
        return roomService.createRoom(request = request, memberId = memberId)
    }

    @ApiOperation("방에 대한 정보를 수정합니다")
    @Auth(allowedAnonymous = false)
    @PutMapping("/api/v1/rooms/{roomId}")
    fun updateRoomInfo(
        @PathVariable roomId: Long,
        @Valid @RequestBody request: RoomUpdateRequest,
        @MemberId memberId: Long,
    ): RoomDetailInfoResponse {
        return roomService.updateRoomInfo(roomId = roomId, request = request, memberId = memberId)
    }

    @ApiOperation("방을 삭제합니다")
    @Auth(allowedAnonymous = false)
    @DeleteMapping("/api/v1/rooms/{roomId}")
    fun deleteRoom(
        @PathVariable roomId: Long,
        @MemberId memberId: Long,
    ): String {
        roomService.deleteRoom(roomId = roomId, memberId = memberId)
        return SUCCESS
    }

    @ApiOperation("내가 생성한 방을 조회합니다 (계정에 연결된 사용자만 가능)")
    @Auth(allowedAnonymous = false)
    @GetMapping("/api/v1/rooms")
    fun retrieveMyRoom(
        @MemberId memberId: Long,
    ): CreatedRoomResponse {
        return roomService.retrieveMyRoom(memberId = memberId)
    }

    @ApiOperation("특정 방에 대한 정보를 조회합니다 (참여자만 가능)")
    @Auth
    @GetMapping("/api/v1/rooms/{roomId}")
    fun retrieveRoomDetailInfo(
        @PathVariable roomId: Long,
        @MemberId memberId: Long,
    ): RoomDetailInfoResponse {
        return roomService.retrieveRoomDetailInfo(roomId = roomId, memberId = memberId)
    }

}
