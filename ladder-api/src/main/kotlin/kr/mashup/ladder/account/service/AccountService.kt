package kr.mashup.ladder.account.service

import kr.mashup.ladder.account.dto.request.CreateAccountRequest
import kr.mashup.ladder.account.dto.request.UpdateAccountInfoRequest
import kr.mashup.ladder.account.dto.response.AccountInfoResponse
import kr.mashup.ladder.domain.account.domain.Account
import kr.mashup.ladder.domain.account.domain.AccountConflictException
import kr.mashup.ladder.domain.account.domain.AccountNotFoundException
import kr.mashup.ladder.domain.account.domain.SocialType
import kr.mashup.ladder.domain.account.infra.jpa.AccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(
    private val accountRepository: AccountRepository,
) {

    @Transactional(readOnly = true)
    fun retrieveAccountInfo(accountId: Long): AccountInfoResponse {
        val account = findAccountById(accountRepository, accountId)
        return AccountInfoResponse.of(account)
    }

    @Transactional
    fun updateAccountInfo(request: UpdateAccountInfoRequest, accountId: Long) {
        val account = findAccountById(accountRepository, accountId)
        account.update(request.nickname, request.profileUrl)
    }

    @Transactional
    fun createAccount(request: CreateAccountRequest): Long {
        validateNotExistsAccount(
            accountRepository = accountRepository,
            socialId = request.socialId,
            socialType = request.socialType
        )
        val account = accountRepository.save(request.toEntity())
        return account.id
    }

}


fun findAccountById(accountRepository: AccountRepository, accountId: Long): Account {
    return accountRepository.findByIdOrNull(accountId)
        ?: throw AccountNotFoundException("해당하는 계정(${accountId})은 존재하지 않습니다")
}

fun validateNotExistsAccount(accountRepository: AccountRepository, socialId: String, socialType: SocialType) {
    if (accountRepository.existsBySocialIdAndSocialType(
            socialId = socialId,
            socialType = socialType
        )
    ) {
        throw AccountConflictException("이미 존재하는 계정 (${socialId}-${socialType}) 입니다")
    }
}
