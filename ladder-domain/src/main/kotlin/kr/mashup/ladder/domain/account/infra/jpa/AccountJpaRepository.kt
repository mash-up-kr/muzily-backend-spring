package kr.mashup.ladder.domain.account.infra.jpa

import kr.mashup.ladder.domain.account.domain.Account
import kr.mashup.ladder.domain.account.domain.AccountRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountJpaRepository : JpaRepository<Account, Long>, AccountRepository
