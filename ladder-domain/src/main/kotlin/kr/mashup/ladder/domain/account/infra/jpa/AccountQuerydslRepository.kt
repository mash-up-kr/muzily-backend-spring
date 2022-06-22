package kr.mashup.ladder.domain.account.infra.jpa

import com.querydsl.jpa.impl.JPAQueryFactory
import kr.mashup.ladder.domain.account.domain.Account
import kr.mashup.ladder.domain.account.domain.AccountQueryRepository
import kr.mashup.ladder.domain.account.domain.QAccount.account
import kr.mashup.ladder.domain.account.domain.SocialType
import org.springframework.stereotype.Repository

@Repository
class AccountQuerydslRepository(
    private val queryFactory: JPAQueryFactory,
) : AccountQueryRepository {

    override fun findSocialIdAndSocialType(socialId: String, socialType: SocialType): Account? {
        return queryFactory.selectFrom(account)
            .where(
                account.socialInfo.socialId.eq(socialId),
                account.socialInfo.socialType.eq(socialType)
            ).fetchOne()
    }

}
