package kr.mashup.ladder.common.controller

import kr.mashup.ladder.common.dto.response.ApiResponse
import kr.mashup.ladder.config.resolver.ACCOUNT_ID
import kr.mashup.ladder.domain.account.domain.Account
import kr.mashup.ladder.domain.account.domain.AccountQueryRepository
import kr.mashup.ladder.domain.account.domain.AccountRepository
import kr.mashup.ladder.domain.account.domain.SocialType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpSession

private const val MOCK_SOCIAL_ID = "TEST_SOCIAL_ID"
private val MOCK_SOCIAL_TYPE = SocialType.KAKAO

@RestController
class LocalTestController(
    private val accountRepository: AccountRepository,
    private val accountQueryRepository: AccountQueryRepository, // TODO: QueryDSL 전용 쿼리들 어떻게 처리해야하지?
    private val httpSession: HttpSession,
) {

    @GetMapping("/api/test/session")
    fun getSession(): ApiResponse<String> {
        val account = accountQueryRepository.findSocialIdAndSocialType(
            socialType = MOCK_SOCIAL_TYPE,
            socialId = MOCK_SOCIAL_ID
        ) ?: accountRepository.save(Account.of(socialId = MOCK_SOCIAL_ID,
            socialType = MOCK_SOCIAL_TYPE,
            nickname = "테스트 계정"
        ))

        httpSession.setAttribute(ACCOUNT_ID, account.id)
        return ApiResponse.ok(httpSession.id)
    }

}
