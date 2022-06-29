package kr.mashup.ladder.domain.account.infra.querydsl

import kr.mashup.ladder.domain.account.domain.Account
import kr.mashup.ladder.domain.account.domain.SocialType

interface AccountQueryRepository {

    fun existsAccountById(accountId: Long): Boolean

    fun existsBySocialIdAndSocialType(socialId: String, socialType: SocialType): Boolean

    fun findBySocialIdAndSocialType(socialId: String, socialType: SocialType): Account?

}
