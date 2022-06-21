package kr.mashup.ladder.account.service

import kr.mashup.ladder.account.dto.response.AccountInfoResponse
import kr.mashup.ladder.domain.account.AccountRepository
import kr.mashup.ladder.domain.common.error.ErrorCode
import kr.mashup.ladder.domain.common.error.model.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(
    private val accountRepository: AccountRepository,
) {

    @Transactional(readOnly = true)
    fun retrieveAccountInfo(accountId: Long): AccountInfoResponse {
        val account = accountRepository.findByIdOrNull(accountId)
            ?: throw NotFoundException("해당하는 계정(${accountId})은 존재하지 않습니다", ErrorCode.NOT_FOUND)
        return AccountInfoResponse.of(account)
    }

}
