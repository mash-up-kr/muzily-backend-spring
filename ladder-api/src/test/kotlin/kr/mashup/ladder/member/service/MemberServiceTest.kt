package kr.mashup.ladder.member.service

import kr.mashup.ladder.IntegrationTest
import kr.mashup.ladder.domain.member.domain.Member
import kr.mashup.ladder.domain.member.exception.MemberNotFoundException
import kr.mashup.ladder.domain.member.infra.jpa.MemberRepository
import kr.mashup.ladder.member.dto.request.UpdateMemberInfoRequest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

internal class MemberServiceTest(
    private val memberRepository: MemberRepository,
    private val memberService: MemberService,
) : IntegrationTest() {

    @Test
    fun `멤버 정보를 수정한다`() {
        // given
        val member = Member(
            nickname = "닉네임",
        )
        memberRepository.save(member)

        val request = UpdateMemberInfoRequest(
            nickname = "새로운 닉네임",
            profileUrl = "https://profile.png"
        )

        // when
        memberService.updateMemberInfo(request = request, memberId = member.id)

        // then
        val members = memberRepository.findAll()
        assertThat(members).hasSize(1)
        members[0].also {
            assertThat(it.nickname).isEqualTo(request.nickname)
            assertThat(it.profileUrl).isEqualTo(request.profileUrl)
            assertThat(it.id).isEqualTo(member.id)
        }
    }

    @Test
    fun `존재하지 않는 멤버에 대해서 멤버 정보를 수정할 수 없다`() {
        // given
        val notFoundMemberId = -1L
        val request = UpdateMemberInfoRequest(
            nickname = "새로운 닉네임",
            profileUrl = "https://profile.png"
        )

        // when & then
        assertThatThrownBy {
            memberService.updateMemberInfo(request, memberId = notFoundMemberId)
        }.isInstanceOf(MemberNotFoundException::class.java)
    }

}
