package kr.mashup.ladder.auth.service

import kr.mashup.ladder.auth.dto.request.AuthRequest
import kr.mashup.ladder.auth.external.KaKaoAuthApiClient
import kr.mashup.ladder.auth.external.kakao.dto.response.KaKaoInfoResponse
import kr.mashup.ladder.auth.external.dto.response.KaKaoTokenResponse
import kr.mashup.ladder.domain.member.domain.Member
import kr.mashup.ladder.domain.member.domain.SocialType
import kr.mashup.ladder.domain.member.infra.jpa.MemberRepository
import org.springframework.stereotype.Service

private val SOCIAL_TYPE = SocialType.KAKAO

@Service
class KaKaoAuthService(
    private val memberRepository: MemberRepository,
    private val kaKaoAuthApiClient: KaKaoAuthApiClient,
) : AuthService {

    override fun authentication(request: AuthRequest): Long {
        val kaKaoProfile: KaKaoInfoResponse =
            getKaKaoProfileInfo(code = request.code, redirectUri = request.redirectUri)
        val member: Member? =
            memberRepository.findMemberBySocialIdAndSocialType(socialId = kaKaoProfile.id, socialType = SOCIAL_TYPE)
        member?.let {
            return member.id
        }
        return signup(kaKaoProfile)

    }

    private fun signup(kaKaoProfile: KaKaoInfoResponse): Long {
        val member = Member.newInstance(
            socialId = kaKaoProfile.id,
            socialType = SOCIAL_TYPE,
            nickname = kaKaoProfile.kakaoAccount.profile.nickname,
            profileUrl = kaKaoProfile.kakaoAccount.profile.profileImage
        )
        memberRepository.save(member)
        return member.id
    }

    private fun getKaKaoProfileInfo(code: String, redirectUri: String): KaKaoInfoResponse {
        val response: KaKaoTokenResponse =
            kaKaoAuthApiClient.getAccessToken(code = code, redirectUri = redirectUri)
        return kaKaoAuthApiClient.getProfileInfo(accessToken = response.accessToken)
    }

}
