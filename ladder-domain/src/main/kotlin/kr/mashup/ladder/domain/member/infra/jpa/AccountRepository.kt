package kr.mashup.ladder.domain.member.infra.jpa

import kr.mashup.ladder.domain.member.domain.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repository For Test
 */
@Repository
interface AccountRepository : JpaRepository<Account, Long>
