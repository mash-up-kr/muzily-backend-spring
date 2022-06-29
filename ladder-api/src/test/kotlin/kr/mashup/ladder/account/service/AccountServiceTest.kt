package kr.mashup.ladder.account.service

import kr.mashup.ladder.IntegrationTest
import kr.mashup.ladder.account.dto.request.UpdateAccountInfoRequest
import kr.mashup.ladder.domain.account.domain.Account
import kr.mashup.ladder.domain.account.domain.AccountNotFoundException
import kr.mashup.ladder.domain.account.domain.AccountSocialInfo
import kr.mashup.ladder.domain.account.domain.SocialType
import kr.mashup.ladder.domain.account.infra.jpa.AccountRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

internal class AccountServiceTest(
    private val accountRepository: AccountRepository,
    private val accountService: AccountService,
) : IntegrationTest() {

    @Test
    fun `회원의 계정 정보를 수정한다`() {
        // given
        val account = Account(
            socialInfo = AccountSocialInfo(socialId = "social-id", socialType = SocialType.KAKAO),
            nickname = "닉네임",
        )
        accountRepository.save(account)

        val request = UpdateAccountInfoRequest(
            nickname = "새로운 닉네임",
            profileUrl = "https://profile.png"
        )

        // when
        accountService.updateAccountInfo(request = request, accountId = account.id)

        // then
        val accounts = accountRepository.findAll()
        assertThat(accounts).hasSize(1)
        accounts[0].also {
            assertThat(it.nickname).isEqualTo(request.nickname)
            assertThat(it.profileUrl).isEqualTo(request.profileUrl)
            assertThat(it.id).isEqualTo(account.id)
            assertThat(it.socialInfo).isEqualTo(account.socialInfo)
        }
    }

    @Test
    fun `존재하지 않는 계정에 대해서 계정 정보를 수정할 수 없다`() {
        // given
        val notFoundAccountId = -1L
        val request = UpdateAccountInfoRequest(
            nickname = "새로운 닉네임",
            profileUrl = "https://profile.png"
        )

        // when & then
        assertThatThrownBy {
            accountService.updateAccountInfo(request, accountId = notFoundAccountId)
        }.isInstanceOf(AccountNotFoundException::class.java)
    }

}
