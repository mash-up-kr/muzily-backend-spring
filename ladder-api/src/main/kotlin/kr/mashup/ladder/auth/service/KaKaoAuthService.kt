package kr.mashup.ladder.auth.service

import kr.mashup.ladder.account.dto.request.CreateAccountRequest
import kr.mashup.ladder.account.service.AccountService
import kr.mashup.ladder.auth.dto.request.AuthRequest
import kr.mashup.ladder.domain.account.domain.SocialType
import kr.mashup.ladder.domain.account.infra.jpa.AccountRepository
import kr.mashup.ladder.external.kakao.KaKaoAuthApiClient
import kr.mashup.ladder.external.kakao.dto.response.KaKaoTokenResponse
import kr.mashup.ladder.external.kakao.dto.response.KaKaoInfoResponse
import org.springframework.stereotype.Service

private val SOCIAL_TYPE = SocialType.KAKAO

@Service
class KaKaoAuthService(
    private val accountService: AccountService,
    private val accountRepository: AccountRepository,
    private val kaKaoAuthApiClient: KaKaoAuthApiClient,
) : AuthService {

    override fun login(request: AuthRequest): Long {
        val kaKaoProfile: KaKaoInfoResponse =
            getKaKaoProfileInfo(code = request.code, redirectUri = request.redirectUri)
        accountRepository.findBySocialIdAndSocialType(socialId = kaKaoProfile.id, socialType = SOCIAL_TYPE)
            ?.let { account ->
                return account.id
            }
        return signup(kaKaoProfile)

    }

    private fun signup(kaKaoProfile: KaKaoInfoResponse): Long {
        return accountService.createAccount(request = CreateAccountRequest(
            socialType = SOCIAL_TYPE,
            socialId = kaKaoProfile.id,
            nickname = kaKaoProfile.kakaoAccount.profile.nickname,
            profileUrl = kaKaoProfile.kakaoAccount.profile.profileImage,
        ))
    }

    private fun getKaKaoProfileInfo(code: String, redirectUri: String): KaKaoInfoResponse {
        val response: KaKaoTokenResponse =
            kaKaoAuthApiClient.getAccessToken(code = code, redirectUri = redirectUri)
        return kaKaoAuthApiClient.getProfileInfo(accessToken = response.accessToken)
    }

}
