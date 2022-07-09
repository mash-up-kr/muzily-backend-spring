package kr.mashup.ladder

import kr.mashup.ladder.domain.member.domain.Member
import kr.mashup.ladder.domain.member.domain.SocialType
import kr.mashup.ladder.domain.member.infra.jpa.MemberRepository
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired

abstract class SetupMemberIntegrationTest : IntegrationTest() {

    @Autowired
    protected lateinit var memberRepository: MemberRepository

    @BeforeEach
    fun setupMember() {
        memberRepository.save(member)
    }

    companion object {
        @JvmStatic
        protected val member: Member = Member.newInstance(
            socialType = SocialType.KAKAO,
            socialId = "social-id",
            nickname = "소셜 계정이 연결된 닉네임"
        )
    }

}
