package kr.mashup.ladder.member.controller

import io.swagger.annotations.ApiOperation
import kr.mashup.ladder.config.annotation.Auth
import kr.mashup.ladder.config.annotation.MemberId
import kr.mashup.ladder.member.dto.request.UpdateMemberInfoRequest
import kr.mashup.ladder.member.dto.response.MemberInfoResponse
import kr.mashup.ladder.member.service.MemberService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class MemberApiController(
    private val memberService: MemberService,
) {

    @ApiOperation("나의 회원 정보를 조회합니다")
    @Auth
    @GetMapping("/api/v1/members")
    fun getMyMemberInfo(
        @MemberId memberId: Long,
    ): MemberInfoResponse {
        return memberService.retrieveMemberInfo(memberId)
    }

    @ApiOperation("나의 회원 정보를 수정합니다")
    @Auth
    @PutMapping("/api/v1/members")
    fun updateMyMemberInfo(
        @Valid @RequestBody request: UpdateMemberInfoRequest,
        @MemberId memberId: Long,
    ): MemberInfoResponse {
        return memberService.updateMemberInfo(request, memberId)
    }

}
