package kr.mashup.ladder.domain.account

import kr.mashup.ladder.domain.account.repository.AccountRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long>, AccountRepositoryCustom
