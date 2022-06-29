package kr.mashup.ladder.auth.service

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import kr.mashup.ladder.IntegrationTest
import kr.mashup.ladder.auth.dto.request.AuthRequest
import kr.mashup.ladder.domain.account.domain.Account
import kr.mashup.ladder.domain.account.domain.AccountSocialInfo
import kr.mashup.ladder.domain.account.domain.SocialType
import kr.mashup.ladder.domain.account.infra.jpa.AccountRepository
import kr.mashup.ladder.external.kakao.KaKaoAuthApiClient
import kr.mashup.ladder.external.kakao.dto.response.KaKaoAccountResponse
import kr.mashup.ladder.external.kakao.dto.response.KaKaoInfoResponse
import kr.mashup.ladder.external.kakao.dto.response.KaKaoProfileResponse
import kr.mashup.ladder.external.kakao.dto.response.KaKaoTokenResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class KaKaoAuthServiceTest(
    private val kaKaoAuthService: KaKaoAuthService,
    private val accountRepository: AccountRepository,
) : IntegrationTest() {

    @MockkBean
    private lateinit var kaKaoAuthApiClient: KaKaoAuthApiClient

    @BeforeEach
    fun mockKaKaoApiClient() {
        every { kaKaoAuthApiClient.getAccessToken(any(), any()) } returns KAKAO_TOKEN_RESPONSE
        every { kaKaoAuthApiClient.getProfileInfo(any()) } returns KAKAO_PROFILE_RESPONSE
    }

    @Test
    fun `카카오 인증시 해당하는 계정이 없는 경우 회원가입된다`() {
        // given
        val request = AuthRequest(
            code = "code",
            redirectUri = "https://redirect.com",
            socialType = SocialType.KAKAO
        )

        // when
        kaKaoAuthService.authentication(request)

        // then
        val accounts = accountRepository.findAll()
        assertThat(accounts).hasSize(1)
        accounts[0].also {
            assertThat(it.socialInfo.socialId).isEqualTo(SOCIAL_ID)
            assertThat(it.socialInfo.socialType).isEqualTo(SocialType.KAKAO)
            assertThat(it.nickname).isEqualTo(NICKNAME)
            assertThat(it.profileUrl).isEqualTo(PROFILE_IMAGE)
        }
    }

    @Test
    fun `카카오 인증시 이미 해당 계정이 존재하는 경우 로그인된다`() {
        // given
        val account = Account(
            socialInfo = AccountSocialInfo(socialId = SOCIAL_ID, socialType = SocialType.KAKAO),
            nickname = "닉네임",
            profileUrl = "http://profile.png"
        )
        accountRepository.save(account)

        val request = AuthRequest(
            code = "code",
            redirectUri = "https://redirect.com",
            socialType = SocialType.KAKAO
        )

        // when
        kaKaoAuthService.authentication(request)

        // then
        val accounts = accountRepository.findAll()
        assertThat(accounts).hasSize(1)
        accounts[0].also {
            assertThat(it.socialInfo.socialId).isEqualTo(SOCIAL_ID)
            assertThat(it.socialInfo.socialType).isEqualTo(SocialType.KAKAO)
            assertThat(it.nickname).isEqualTo(account.nickname)
            assertThat(it.profileUrl).isEqualTo(account.profileUrl)
        }
    }

    companion object {
        private const val SOCIAL_ID = "kakao-id"
        private const val NICKNAME = "nickname"
        private const val PROFILE_IMAGE = "https://profile.png"

        private val KAKAO_TOKEN_RESPONSE = KaKaoTokenResponse(
            accessToken = "access-token"
        )

        private val KAKAO_PROFILE_RESPONSE = KaKaoInfoResponse(
            id = SOCIAL_ID,
            kakaoAccount = KaKaoAccountResponse(
                email = "will.seungho@gmail.com",
                profile = KaKaoProfileResponse(
                    nickname = NICKNAME,
                    profileImage = PROFILE_IMAGE
                )
            )
        )
    }

}
