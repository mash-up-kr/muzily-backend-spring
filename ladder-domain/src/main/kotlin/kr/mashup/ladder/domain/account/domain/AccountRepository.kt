package kr.mashup.ladder.domain.account.domain

interface AccountRepository {

    fun save(account: Account): Account

    fun findById(accountId: Long): Account?

    fun findAll(): List<Account>

}
