package kr.mashup.ladder.account.controller

import kr.mashup.ladder.account.dto.response.AccountInfoResponse
import kr.mashup.ladder.account.service.AccountService
import kr.mashup.ladder.common.dto.response.ApiResponse
import kr.mashup.ladder.config.annotation.AccountId
import kr.mashup.ladder.config.annotation.Auth
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
    private val accountService: AccountService,
) {

    @Auth
    @GetMapping("/api/v1/account")
    fun getMyAccountInfo(
        @AccountId accountId: Long,
    ): ApiResponse<AccountInfoResponse> {
        return ApiResponse.ok(accountService.retrieveAccountInfo(accountId))
    }

}
