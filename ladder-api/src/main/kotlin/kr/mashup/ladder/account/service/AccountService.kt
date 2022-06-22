package kr.mashup.ladder.account.service

import kr.mashup.ladder.account.dto.request.UpdateAccountInfoRequest
import kr.mashup.ladder.account.dto.response.AccountInfoResponse
import kr.mashup.ladder.domain.account.domain.Account
import kr.mashup.ladder.domain.account.domain.AccountNotFoundException
import kr.mashup.ladder.domain.account.domain.AccountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(
    private val accountRepository: AccountRepository,
) {

    @Transactional(readOnly = true)
    fun retrieveAccountInfo(accountId: Long): AccountInfoResponse {
        val account = findAccountById(accountId)
        return AccountInfoResponse.of(account)
    }

    @Transactional
    fun updateAccountInfo(request: UpdateAccountInfoRequest, accountId: Long) {
        val account = findAccountById(accountId)
        account.update(request.nickname, request.profileUrl)
    }

    private fun findAccountById(accountId: Long): Account {
        return accountRepository.findById(accountId)
            ?: throw AccountNotFoundException("해당하는 계정(${accountId})은 존재하지 않습니다")
    }

}
