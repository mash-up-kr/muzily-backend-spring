package kr.mashup.ladder.account.controller

import kr.mashup.ladder.account.dto.request.UpdateAccountInfoRequest
import kr.mashup.ladder.account.dto.response.AccountInfoResponse
import kr.mashup.ladder.account.service.AccountService
import kr.mashup.ladder.config.annotation.AccountId
import kr.mashup.ladder.config.annotation.Auth
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class AccountApiController(
    private val accountService: AccountService,
) {

    @Auth
    @GetMapping("/api/v1/accounts")
    fun getMyAccountInfo(
        @AccountId accountId: Long,
    ): AccountInfoResponse {
        return accountService.retrieveAccountInfo(accountId)
    }

    @Auth
    @PutMapping("/api/v1/accounts")
    fun updateMyAccountInfo(
        @Valid @RequestBody request: UpdateAccountInfoRequest,
        @AccountId accountId: Long,
    ) {
        accountService.updateAccountInfo(request, accountId)
    }

}
