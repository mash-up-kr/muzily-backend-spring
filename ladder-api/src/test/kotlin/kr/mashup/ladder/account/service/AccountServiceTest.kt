package kr.mashup.ladder.account.service

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.mashup.ladder.account.dto.request.UpdateAccountInfoRequest
import kr.mashup.ladder.domain.account.Account
import kr.mashup.ladder.domain.account.AccountRepository
import kr.mashup.ladder.domain.account.AccountSocialInfo
import kr.mashup.ladder.domain.account.SocialType
import kr.mashup.ladder.domain.common.error.model.NotFoundException
import kr.mashup.ladder.util.DatabaseCleanup
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest
internal class AccountServiceTest(
    private val accountService: AccountService,
    private val accountRepository: AccountRepository,
    private val databaseCleanup: DatabaseCleanup,
) : FunSpec({

    // TODO: IntegrationTest 상속 받아서 처리할 수 없을까?
    afterEach {
        withContext(Dispatchers.IO) {
            databaseCleanup.execute()
        }
    }

    context("회원의 계정 정보를 수정한다") {
        test("회원의 계정 정보를 수정하면 DB에 변경된다") {
            // given
            val account = Account(
                socialInfo = AccountSocialInfo(socialId = "social-id", socialType = SocialType.KAKAO),
                nickname = "닉네임",
            )
            withContext(Dispatchers.IO) {
                accountRepository.save(account)
            }

            val request = UpdateAccountInfoRequest(
                nickname = "새로운 닉네임",
                profileUrl = "https://profile.png"
            )

            // when
            withContext(Dispatchers.IO) {
                accountService.updateAccountInfo(request = request, accountId = account.id)
            }

            // then
            val accounts = withContext(Dispatchers.IO) {
                accountRepository.findAll()
            }
            accounts shouldHaveSize 1
            accounts[0].let {
                it.nickname shouldBe request.nickname
                it.profileUrl shouldBe request.profileUrl
                it.id shouldBe account.id
                it.socialInfo shouldBe account.socialInfo
            }
        }

        test("회원 정보 수정시 존재하지 않는 회원이면 NotFoundException이 발생한다") {
            // given
            val notFoundAccountId = -1L
            val request = UpdateAccountInfoRequest(
                nickname = "새로운 닉네임",
                profileUrl = "https://profile.png"
            )

            // when & then
            shouldThrowExactly<NotFoundException> {
                accountService.updateAccountInfo(request, accountId = notFoundAccountId)
            }
        }
    }

})
