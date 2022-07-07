package kr.mashup.ladder.auth.service

import kr.mashup.ladder.domain.member.domain.Member
import kr.mashup.ladder.domain.member.infra.jpa.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GuestAuthService(
    private val memberRepository: MemberRepository,
) {

    @Transactional
    fun createAnonymousMember(): Long {
        val member = memberRepository.save(Member.newAnonymous())
        return member.id
    }

}
