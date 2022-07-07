package kr.mashup.ladder.member.service

import kr.mashup.ladder.domain.member.domain.Member
import kr.mashup.ladder.domain.member.domain.MemberNotFoundException
import kr.mashup.ladder.domain.member.infra.jpa.MemberRepository
import kr.mashup.ladder.member.dto.request.UpdateMemberInfoRequest
import kr.mashup.ladder.member.dto.response.MemberInfoResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {

    @Transactional(readOnly = true)
    fun retrieveMemberInfo(memberId: Long): MemberInfoResponse {
        val member = findMemberById(memberId)
        return MemberInfoResponse.of(member)
    }

    @Transactional
    fun updateMemberInfo(request: UpdateMemberInfoRequest, memberId: Long) {
        val member = findMemberById(memberId)
        member.update(request.nickname, request.profileUrl)
    }

    private fun findMemberById(memberId: Long): Member {
        return memberRepository.findByIdOrNull(memberId)
            ?: throw MemberNotFoundException("해당하는 멤버(${memberId})은 존재하지 않습니다")
    }

}
